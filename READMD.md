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
  4. EKS 클러스터 생성
     5. EKS 클러스터 생성
        1. 클러스터 생성
           ```agsl
            eksctl create cluster --name my-cluster --region ap-northeast-2
            ``` 
        2. k8s 작성
        ```
        apiVersion: apps/v1
        kind: Deployment
        metadata:
            name: myapp
        spec:
            selector:
                matchLabels:
                    app: myapp
            template:
                metadata:
                    labels:
                        app: myapp
                spec:
                    containers:
                        - name: myapp
                        image: 111111111111.dkr.ecr.us-east-2.amazonaws.com/myapp:latest
                    resources:
                        limits:
                            memory: "128Mi"
                            cpu: "500m"
                        ports:
                          - name: tcp
                          containerPort: 8080
        ---
        apiVersion: v1
        kind: Service
        metadata:
            name: myapp
        spec:
            selector:
                app: myapp
            ports:
                - port: 80
                targetPort: 8080
            type: LoadBalancer
        ```
        3. k8s 배포
        ```
        kubectl apply -f k8s.yaml
        ```
        4. k8s 확인
        ```
        kubectl get all
        ```
        5. k8s 삭제
        ```
        kubectl delete -f k8s.yaml
        ```
        6. 클러스터 삭제
        ```
        eksctl delete cluster --name my-cluster --region ap-northeast-2
        ```
        