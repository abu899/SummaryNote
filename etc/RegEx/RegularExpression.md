# 정규 표현식

## 기본 문자

기본적으로 정규표현식에 문자를 입력하면 문자에 해당하는 단어를 찾게 된다. 기본적으로 `Case sensitive`로 동작.

### Example

Source : Hello world

- Case 1
  - RegEx : Hello
  - First Match : `Hello` world
  - All Match : `Hello` world
- Case 2
  - RegEx : hello
  - First Match : null
  - All Match : null

## 문자열의 위치와 이스케이프

문자 `^`와 `$`를 이용해 문자열의 위치를 지정한다.
- ^
  - `^ + 단어`로 시작되는 대상을 찾음
- $
  - `단어 + $`로 끝나는 대상을 찾음
- 만약 ^나 $를 정규식으로 검출하고 싶다면 `\(Escape)`와 함꼐 사용해야함
  - `\`는 뒤에 나오는 문자를 정규표현식의 이미 할당된 기능을 사용하는 역할이 아닌 순수 문자로 인식

### Example

Source : who is who

- Case 1
  - RegEx : ^who
  - First Match : `who` is who
  - All Match : `who` is who
- Case 2
  - RegEx : who$
  - First Match : who is `who`
  - All Match : who is `who`

Source : $12$ \-\ $25$

- Case 1
  - RegEx : ^$
  - First Match : null
  - All Match : null
- Case 2
  - RegEx : \\$
  - First Match : `$`12$ \-\ $25$
  - All Match : `$`12`$` \-\ `$`25`$`
- Case 3
  - RegEx : ^\\$
  - First Match : `$`12$ \\-\ $25$
  - All Match : `$`12$ \\-\ $25$
- Case 4
    - RegEx : \\$$
    - First Match : $12$ \\-\ $25`$`
    - All Match : $12$ \\-\ $25`$`
- Case 5
    - RegEx : \\
    - First Match : $12$ `\ `-\ $25`$
    - All Match : $12$ `\ `-`\ ` $25$

## 어떤 문자(Any Character)

포인트 `.`을 이용하면 .의 갯수에 해당하는 문자를 매칭

### Example

Source : Regular expressions are powerful!!

- Case 1
  - RegEx: .
  - First Match: `R`egular expressions are powerful!!
  - All Match : `Regular expressions are powerful!!`
- Case 2
  - RegEx: ......
  - First Match: `Regula`r expressions are powerful!!
  - All Match : `Regular expressions are powerf`ul!!
    - 6개의 문자씩 묶어서 매칭했을 때 뒤에 4글자는 남게됨

Source : O.K.

- Case 1
  - RegEx: .
  - First Match: `O`.K.
  - All Match: `O.K.`
- Case 2
  - RegEx: \\.
  - First Match: O`.`K.
  - All Match: O`.`K`.`
- Case 3
  - RegEx: \\..\.
  - First Match: O`.K.`
  - All Match: O`.K.`

## 원하는 문자의 후보군 지정

`[]`를 이용해서 [] 내에 존재하는 문자와 일치하는 `문자 1개`를 찾는 정규 표현식을 작성할 수 있다.

- 문자 1개가 아닌 범위를 지정하기 위해선 [ `-` ] 대괄호 내 - 를 사용한다.
- 대괄호 내 `^`이 같이 쓰이게 되면 Not 의 의미가 된다 

### Example

Source: How do you do?

- Case 1
  - RegEx: [oyu]
  - First Match : H`o`w do you do?
  - All Match : H`o`w d`o` `you` d`o`?
- Case 2
  - RegEx: [dH].
  - First Match : `Ho`w do you do?
  - All Match : `Ho`w `do` you `do`?
- Case 3
  - RegEx: [owy][yow]
  - First Match : H`ow` do you do?
  - All Match : H`ow` do `yo`u do?

Source: 
ABCDEFGHIJKLMNOPQRSTUVWXYZ
abcdefghijklmnopqrstuvwxyz 0123456789

- Case 1
  - RegEx: [C-K]
  - First Match: AB`C`DEFGHIJKLMNOPQRSTUVWXYZ
    abcdefghijklmnopqrstuvwxyz 0123456789
  - All match: AB`CDEFGHIJK`LMNOPQRSTUVWXYZ
    abcdefghijklmnopqrstuvwxyz 0123456789
- Case 2
  - RegEx: [C-Ka-d2-6]
  - First Match: AB`C`DEFGHIJKLMNOPQRSTUVWXYZ
    abcdefghijklmnopqrstuvwxyz 0123456789
  - All match: AB`CDEFGHIJK`LMNOPQRSTUVWXYZ
    `abcd`efghijklmnopqrstuvwxyz 01`23456`789
- Case 3
  - RegEx: [^W-Z]
  - First Match : `A`BCDEFGHIJKLMNOPQRSTUVWXYZ
    abcdefghijklmnopqrstuvwxyz 0123456789
  - All Match : `ABCDEFGHIJKLMNOPQRSTUV`WXYZ
    `abcdefghijklmnopqrstuvwxyz 0123456789`

## 서브 패턴
`()와 |` 를 쓰게 되면 or 의 의미를 가지고 이에 해당하는 문자를 찾게 됨

### Example

Source: Monday Tuesday Friday

- Case 1
  - RegEx: (on|ues|rida)
  - First Match: M`on`day Tuesday Friday
  - All Match: M`on`day T`ues`day F`rida`y
- Case 2
    - RegEx: (on|ues|rida)day
    - First Match: `Monday` Tuesday Friday
    - All Match: `Monday` `Tuesday` `Friday`
- Case 3
    - RegEx: ..(id|esd|nd)ay
    - First Match: `Monday` Tuesday Friday
    - All Match: `Monday` `Tuesday` `Friday`

## 수량자(Quantifiers)

어떤 패턴이 얼마나 등장하는가를 나타내는 정규표현식

- `*`
  - `*`앞에 등장하는 어떤 패턴이 0 ~ 여러개 나올 수 있다
- `+`
  - `+`앞에 등장하는 어떤 패턴이 1 ~ 여러개 나올 수 있다
- `?`
  - `?`앞에 등장하는 어떤 패턴이 0 ~ 1개 나올 수 있다

### Simple Example

Source: aabbc abc bc

- Case 1
  - RegEx: a*b
  - First Match: `aab`bc abc bc
  - All Match: `aab`bc `ab`c `b`c
- Case 2
  - RegEx: a+b
  - First Match: `aab`bc abc bc
  - All Match: `aab`bc `ab`c bc
- Case 3
  - RegEx: a?b
  - First Match: a`ab`bc abc bc
  - All Match: a`ab`bc `ab`c `b`c

### Example of *

Source: -@- ***--" *"-- ***-@-

- Case 1
  - RegEx: .*
  - First Match: `-@-***--" *"-- ***-@-`
  - All Match: `-@-***--" *"-- ***-@-`
- Case 2
  - RegEx: -A*-
  - First Match: -@-***`--`" *"-- ***-@-
  - All Match: -@-***`--`" *"`--` ***-@-
- Case 3
  - RegEx: [-@]*
  - First Match: `-@-`***--" *"-- ***-@-
  - All Match: `-@-`***`--`" *"`--` ***`-@-`

### Example of +

Source: -@@@- * ** - - "*" -- * ** -@@@-

- Case 1
  - RegEx: \*+
  - First Match: -@@@- `*` ** - - "*" -- * ** -@@@-
  - All Match: -@@@- `*` `**` - - "`*`" -- `*` `**` -@@@-
- Case 2
  - RegEx: -@+-
  - First Match: `-@@@-` * ** - - "*" -- * ** -@@@-
  - All Match: `-@@@-` * ** - - "*" -- * ** `-@@@-`
- Case 3
  - RegEx: [^ ]+
  - First Match: `-@@@-` * ** - - "*" -- * ** -@@@-
  - All Match: `-@@@-` `*` `**` `-` `-` `*` `--` `*` `**` `-@@@-`

### Example of ?

Source: --XX-@-XX-@@-XX-@@@-XX-@@@@-XX-@@-@@-

- Case 1
  - RegEx: -X?XX?X
  - First Match: -`-XX`-@-XX-@@-XX-@@@-XX-@@@@-XX-@@-@@-
  - All Match: -`-XX`-@`-XX`-@@`-XX`-@@@`-XX`-@@@@`-XX`-@@-@@-
- Case 2
  - RegEx: -@?@?@?-
  - First Match: `--`XX-@-XX-@@-XX-@@@-XX-@@@@-XX-@@-@@-
  - All Match: `--`XX`-@-`XX`-@@-`XX`-@@@-`XX-@@@@-XX`-@@-`@@-

## 수량자 심화

원하는 수량을 정확하게 지정하는 방법으로 `{ 갯수 }`를 사용한다

### Example of {}

Source: One ring to bring them all and in the darkness bind them

- Case 1
  - RegEx: .{5}
  - First Match: `One r`ing to bring them all and in the darkness bind them
  - All Match: `One ring to bring them all and in the darkness bind the`m
- Case 2
  - RegEx: [els]{1,3} -> 1~3개
  - First Match: On`e` ring to bring them all and in the darkness bind them
  - All Match: On`e` ring to bring th`e`m a`ll` and in the darkn`ess` bind th`e`m
- Case 3
  - RegEx: [a-z]{3,} -> 3개 이상
  - First Match: One `ring` to bring them all and in the darkness bind them
  - All Match: One `ring` to `bring` `them` `all` `and` in `the` `darkness` `bind` `them`

### Example of mixed quantifiers

수량자 뒤에 연속으로 ? 수량자가 올 때

Source: One ring to bring them all and in the darkness bind them

- Case 1
  - RegEx: r.*
  - First Match: One `ring to bring them all and in the darkness bind them`
  - All Match: One `ring to bring them all and in the darkness bind them`
- Case 2
  - RegEx: r.*? -> 이 경우 `?`는 앞에 나온 수량자의 가장 적은 범위를 선택, 여기서는 0개
  - First Match: One `r`ing to bring them all and in the darkness bind them
  - All Match: One `r`ing to b`r`ing them all and in the da`r`kness bind them
- Case 3
  - RegEx: r.+
  - First Match: One `ring to bring them all and in the darkness bind them`
  - All Match: One `ring to bring them all and in the darkness bind them`
- Case 4
  - RegEx: r.+? -> Case2 와 같은 방식
  - First Match: One `ri`ng to bring them all and in the darkness bind them
  - All Match: One `ri`ng to b`ri`ng them all and in the da`rk`ness bind them
- Case 5
  - RegEx: r.?
  - First Match: One `ri`ng to bring them all and in the darkness bind them
  - All Match: One `ri`ng to b`ri`ng them all and in the da`rk`ness bind them
- Case 6
  - RegEx: r.??
  - First Match: One `r`ing to bring them all and in the darkness bind them
  - All Match: One `r`ing to b`r`ing them all and in the da`r`kness bind them

## Character Class(경계)

\w -> word(alphabet + numeric + _) == [A-z0-9_]

### Example of word(/w) and Not word(/W)

Source: A1 B2 c3 D_4 e:5 ffGG77--___--

- Case 1
  - RegEx: \w
  - First Match: `A`1 B2 c3 D_4 e:5 ffGG77--___--
  - All Match: `A1` `B2` `c3` `D_4` `e`:`5` `ffGG77`--`___`--
- Case 2
  - RegEx: \w*
  - First Match: `A1` B2 c3 D_4 e:5 ffGG77--___--
  - All Match: `A1` `B2` `c3` `D_4` `e`:`5` `ffGG77`--`___`--
- Case 3
  - RegEx: [a-z]\w*
  - First Match: A1 B2 `c3` D_4 e:5 ffGG77--___--
  - All Match: A1 B2 `c3` `D_4` `e`:5 `ffGG77`--___--
- Case 4
  - RegEx: \w{5}
  - First Match: A1 B2 c3 D_4 e:5 `ffGG7`7--___--
  - All Match: A1 B2 c3 D_4 e:5 `ffGG7`7--___--

Source: AS _34:AS11.23 @#$ %12^*

- Case 1
  - RegEx: \W
  - First Match: AS` `_34:AS11.23 @#$%12^*
  - All Match: AS` `_34`:`AS11.23` @#$%`12`^*`

### Example of Digit(\d) and not Digit(\D)

Source Page 123; published: 1234 id=12#24@112

- Case 1
  - RegEx: \d
  - First Match: Page `1`23; published: 1234 id=12#24@112
  - All Match: Page `123`; published: `1234` id=`12`#`24`@`112`
- Case 2
  - RegEx: \D
  - First Match: `P`age 123; published: 1234 id=12#24@112
  - All Match: `Page `123`; published: `1234` id=`12`#`24`@`112

### Example of Word Boundary (\b)

Source: Ere iron was found or tree was hewn

- Case 1
  - RegEx: \b\w
  - First Match: `E`re iron was found or tree was hewn
  - All Match: `E`re `i`ron `w`as `f`ound `o`r `t`ree `w`as `h`ewn
- Case 2
  - RegEx: \w\b
  - First Match: Er`e` iron was found or tree was hewn
  - All Match: Er`e` iro`n` wa`s` foun`d` o`r` tre`e` wa`s` hew`n`
- Case 3
  - RegEx: \b\w+\b
  - First Match: `Ere` iron was found or tree was hewn
  - All Match: `Ere` `iron` `was` `found` `or` `tree` `was` `hewn`