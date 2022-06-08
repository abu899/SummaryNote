# Calcite core

Apache calcite core 중 수정 빈도가 높거나 중요한 클래스를 우선적으로 정리.

origin : Apache Calcite 1.26.0

## Parser.jj

SQL을 파싱할 때 사용되는 컴파일러이다. Calcite 에서 파싱하지 못했던 연산자나 우리가 지원하는 SQL 문법을 지원하기 위해
파싱해야하는 연산자 등을 추가했으며 추가적으로 파싱된 결과를 처리하는 객체 또는 흐름또한 만들어줘야 한다.
하지만 그보다 앞서 파싱 자체가 되지 않으면 추가 흐름을 만들 수 없기 때문에 파싱 부분을 먼저 수정해줘야한다.
`.jj`문법에 따라 작성되었으며, 주변의 문법들을 참조해 작성하면 된다.

```text
|   <LS> { return SqlStdOperatorTable.LEFT_SHIFT; }
|   <RS> { return SqlStdOperatorTable.RIGHT_SHIFT; }
|   <VERTICAL_BAR> { return SqlStdOperatorTable.BITWISE_OR; }
|   <BITAND> { return SqlStdOperatorTable.BITWISE_AND; }
|   <CARET> { return SqlStdOperatorTable.BITWISE_XOR; }

...

|   < LS: "<<" >
|   < RS: ">>" >
|   < BITAND: "&" >
|   < BITNOT: "~" >
```
Parser.jj`의 상단 부분에 `SqlStdOperatorTable`을 리턴하는 부분은 어떤 객체가 호출될지를 지정하는 부분으로 볼 수있고,
아래 부분에 작성된 부분은 어떤 연산자가 사용될지를 지정하는 부분이다.
만약, 기존에 있는 연산자를 다른 용도로 사용하고 싶다면 위에 부분만 수정하면되고, 신규 연산자를 추가하고 싶다면 아래쪽도
함께 수정해줘야한다.

## SqlStdOperatorTable

파서를 수정하여 신규 연산자나 연산자의 용도 변경을 진행하게 되면 필연적으로 확인해야하는 클래스이다.
클래스명에서 알 수 있듯이 Calcite 에서 사용되는 연산자들이 선언되어 있으며, `객체`이다.
연산자 객체의 종류는 크게 다음으로 구분할 수 있다

- SqlPrefixOperator
- SqlUnaryOperator
- SqlBinaryOperator
- SqlFunction

위에서 보면 `SqlFunction`이 존재하는데, query 에서 사용되는 SUBSTR, LENGTH 등의 함수 또한 연산자로 지정되어 있다.
이 클래스에서는 연산자 선언에서 살펴 볼 점은 객체를 생성할 때 `name`을 지정해주는 것과 `returnType`을 지정해 주는 부분이다.
`name`의 경우 `RelToSql` 즉 RelNode 를 SQL 로 다시 변환했을 때, 어떤 이름으로 변경될 것인지가 지정되는 것이며,
`returnType`은 이 연산, 함수의 결과의 리턴 값을 추정하는 방식을 지정하는 것이다. 보통 입력되는 값을 추종하거나,
AVG 같이 무조건적으로 DOUBLE 을 리턴하는 식으로 형식을 지정할 수 있다.

### SqlDialect, SqlKind, RexImplTable, RexSqlStandardConvertletTable

`SqlStdOperatorTable`에 새롭게 추가된 연산자나 함수를 정의해줘야하는 클래스이다.
- `SqlDialect`
  - `Dialect`에 따라 정의된 연산자가 다르지만, 기본적인 dialect 는 내장된 타입을 쓰기 때문에 추가가 필요하다
- SqlKind
  - 연산자를 `instanceof` 하게되면 어떤 연산자 객체인지를 판단할 수 있는데, 이 내용이 SqlKind
- RexImplTable, RexSqlStandardConvertletTable
  - 이 두 클래스에 등록을 해줘야 파싱된 구문이 정상적으로 `SqlStdOperatorTable`에 등록된 객체와 매핑이 된다.

```text
class RexImplTable {
    defineBinary(LEFT_SHIFT, LeftShift, NullPolicy.STRICT, null);
    defineBinary(RIGHT_SHIFT, RightShift, NullPolicy.STRICT, null);
    ...
}

class RexSqlStandardConvertletTable{
    registerEquivOp(SqlStdOperatorTable.LEFT_SHIFT);
    registerEquivOp(SqlStdOperatorTable.RIGHT_SHIFT);
    ...
}
```

## RexSimplify

수정 빈도가 높지는 않지만, query 가 예상치 못하게 생략되거나 변형되었을 때 살펴볼 수 있는 클래스이다.
calcite 는 query 를 RelNode 로 바꿀 때, config 나 내부적으로 정해둔 규칙에 따라 쿼리를 변경하거나 생략하는 경우가 존재한다.
예를 들면, `AND나 OR로 여러 조건이 등장하는 경우 BETWEEN 이라는 연산자로 변경`하거나 `IN 구문은 LogicalValue + LogicalJoin 으로 변경`
하는 등의 사용자가 의도하지 않는 방향으로 쿼리가 변형될 수 있다. 이런 경우 쿼리내 사용되는 연산자를 현재 클래스 내에서 검색하여 변경내역을 추적하거나
변경을 원하지 않는 경우 이를 주석 또는 config 로 처리하는 식으로 방법을 찾을 수 있다.

## SqlToRelConverter

SqlNode To RelNode, 즉 실제 쿼리가 calcite 에서 처리한 LogicalPlan 으로 변환되는 중요도가 높은 클래스이다.
```text
Planner planner = ParseUtil.getPlanner(frameworkConfig);
SqlNode parsedSql = ParseUtil.parseSql(planner, query);
SqlNode validatedSql = ParseUtil.validateParsedSql(planner, parsedSql);
RelNode sqlRelNode = ParseUtil.convertSqlNodeToRelatedExpression(planner, validatedSql).rel;
```
위에서 `validatedSql`이 `sqlRelNode`로 변환되는데 여기서 `SqlToRelConverter`가 동작한다.
`SqlNode`는 select 절, from 절, where 절, group by 절 등의 또다른 SqlNode 객체들의 집합으로 볼 수 있는데,
`SqlToRelConverter`는 각각의 절들을 순서에 따라 순회하면서 이를 RelNode 로 바꾸는 역할을 진행한다.  

디버그를 할 때 흐름의 시작점으로 잡을만한 메소드는 `convertQuery()`로 이후  `convertQueryRecursive()`를 통해
재귀적으로 각각의 절들을 순회한다. `Nested Query`의 경우, `substituteSubQuery()` 함수를 통해 가장 안쪽까지 순회하게 된다.
내용이 워낙 방대하고 SqlNode 를 RelNode 로 바꾸는 모든 내용이 한 클래스에 존재하기에 `ConvertXXX`로 검색하여 디버깅하는게 도움이 될 수 있다.

```text
Select 절    -> convertSelect
From 절      -> convertFrom
OrderBy 절   -> convertOrder
...
```

## SqlImplementor And RelToSqlConverter


