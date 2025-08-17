pipeline {
  agent any
  tools { maven 'Maven3'; jdk 'JDK17' }
  environment {
    SONARQUBE_SERVER = 'SonarQube'
    DOCKER_USER = 'YOUR_DOCKERHUB_USERNAME'
    IMAGE_TAG = "${env.BUILD_NUMBER}"
  }
  stages {
    stage('Checkout'){ steps { checkout scm } }
    stage('Backend Build + Test'){
      steps { dir('backend'){ bat 'mvn -B -DskipTests=false clean verify' } }
    }
    stage('SonarQube'){
      steps {
        withSonarQubeEnv("${SONARQUBE_SERVER}") {
          dir('backend'){
            bat '''mvn sonar:sonar ^
              -Dsonar.projectKey=secure-pdf-portal-backend ^
              -Dsonar.host.url=%SONAR_HOST_URL% ^
              -Dsonar.login=%SONAR_AUTH_TOKEN%'''
          }
        }
      }
    }
    stage('Docker Build'){
      steps {
        dir('backend'){  bat "docker build -t %DOCKER_USER%/secure-pdf-portal-backend:%IMAGE_TAG% -t %DOCKER_USER%/secure-pdf-portal-backend:latest ." }
        dir('frontend'){ bat "docker build -t %DOCKER_USER%/secure-pdf-portal-frontend:%IMAGE_TAG% -t %DOCKER_USER%/secure-pdf-portal-frontend:latest ." }
      }
    }
    stage('Docker Push'){
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PASS')]){
          bat "echo %DOCKERHUB_PASS% | docker login -u %DOCKERHUB_USER% --password-stdin"
          bat "docker push %DOCKER_USER%/secure-pdf-portal-backend:%IMAGE_TAG%"
          bat "docker push %DOCKER_USER%/secure-pdf-portal-backend:latest"
          bat "docker push %DOCKER_USER%/secure-pdf-portal-frontend:%IMAGE_TAG%"
          bat "docker push %DOCKER_USER%/secure-pdf-portal-frontend:latest"
        }
      }
    }
  }
}
