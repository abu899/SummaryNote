# Elastic Block Storage (EBS)

인스턴스가 CPU 와 메모리라고 하면, EBS 는 인스턴스에 붙는 가상 하드디스크이다.

### 특징

- EC2와 독립적으로 작동가능하기에 인스턴스(EC2)가 동작을 종료나 중지되어 유지가 가능하다
    - EBS 는 네트워크로 별개로 연결된 서비스
    - 따라서 인스턴스를 별개로 업그레이드 시키고, 기존 EBS 를 연결시키는 것도 가능
- 여러개의 EBS 를 하나의 인스턴스에 붙이는 것도 가능
    - 역으로, 여러 개의 인스턴스가 하나의 EBS 로 연결하는 것 또한 가능하다(EBS Multi Attach)

### 볼륨 유형

1. 범용(General Purpose of GP3) : SSD
2. 프로비저닝 된 IOPS(Provisioned IOPS or io2) : SSD
    - IOPS 가 높아서 데이터 통신이 빠름
    - IOPS 가 중요한 서비스나 데이터베이스에서 사용
3. 쓰루풋 최적화(Throughput Optimized HDD or st1)
4. 콜드 HDD(SC1)
    - 파일 저장소
5. 마그네틱(Standard)
    - 백업 또는 비 정기적인 데이터 액세스

### EBS vs Instance Storage

- EBS
    - 인스턴스와 네트워크로 연결
    - 속도가 상대적으로 느림
    - 인스턴스가 삭제되더라도 EBS 는 남아있음
- Instance Storage
    - EC2 내부의 저장소
    - 속도가 빠름
    - 인스턴스가 삭제되면 같이 삭제됨
    - EBS 처럼 다른 인스턴스에 연결하거나 하나의 스토리지에 다른 인스턴스 연결 불가
    - 캐시 데이터와 같은 영구적이지 않은 데이터 저장

