### SPRING BOOT EKS
1. 도커파일 작성

```
FROM openjdk:17
WORKDIR /usr/src/myapp
COPY build/libs/spring_boot_eks-0.0.1-SNAPSHOT.jar /usr/src/myapp
CMD ["java", "-jar", "spring_boot_eks-0.0.1-SNAPSHOT.jar"]
```


2. AWS ECR 권한 설정
   1. IAM > 사용자 > 사용자 추가
      1. 권한정책
           - AmazonEC2ContainerRegistryFullAccess
           - AmazonEC2ContainerServiceFullAccess
           - AdministratorAccess
      
      2. 엑세스 키발급
         - 엑세스 키만들기 
  2. ECR 레포지토리 생성
     - 프라이빗
  3. Docker 빌드
     1. AWS CLI 설치 - 터미널
     2. AWS CLI 설정 - 터미널
        1. aws configure
        2. AWS Access Key ID [None]: 엑세스 키
        3. AWS Secret Access Key [None]: 시크릿 키
        4. Default region name [None]: ap-northeast-2
        5. Default output format [None]: json
     3. Docker 빌드 - 터미널
        1. [M1] docker buildx build --platform linux/amd64 -t spring_boot_eks . 
        2. [INTEL & AMD] docker build -t spring_boot_eks .
        2. docker images
        3. docker tag spring_boot_eks:latest 000000000000.dkr.ecr.ap-northeast-2.amazonaws.com/spring_boot_eks:latest
        4. docker push 000000000000.dkr.ecr.ap-northeast-2.amazonaws.com/spring_boot_eks:latest
  4. VPC 생성
     1. k8s > amazon-eks-vpc-private-subnets.yaml (참조)
     2. 터미널 방식
      ```agsl
      eksctl create cluster -f amazon-eks-vpc-private-subnets.yaml
      ```
     3. cloudformation 방식
        1. amazon-eks-vpc-private-subnets.yaml 파일을 cloudformation에 업로드
        2. cloudformation > 스택 생성
        3. 스택 이름 : eks-vpc
  5. ClusterConfig Eks 파일 작성(예시참조)
     ```
     eksctl create cluster -f eks-config.yaml
     ```
  6. Deployment 작성 (예시참조)
     ```
     kubectl apply -f deployment.yaml
     ```
  7. Service 작성 LBA (예시참조)
     ```
     kubectl apply -f service.yaml
     ```