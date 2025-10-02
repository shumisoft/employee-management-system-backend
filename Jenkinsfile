pipeline {
    agent any
    
    tools {
        jdk 'jdk21'
        maven 'maven3'
        nodejs 'node'
    }

    environment {
        SCANNER_HOME= tool 'sonar-scanner'
    }

    stages {
        stage('Git Pull') {
            steps {
                git branch: 'development', credentialsId: 'git-cred-d', url: 'https://github.com/shumisoft/employee-management-system-backend'
            }
        }
        stage('Maven Compile') {
            steps {
               sh 'mvn compile'
            }
        }
        stage('Trivy Vulnarability Scan') {
            steps {
                sh 'trivy fs -f table -o trivy-ems-be-fs-report.html . && cat trivy-ems-be-fs-report.html'
            }
        }
        stage('Build, Test & SonarQube') {
            steps {
                withSonarQubeEnv('SonarQubeOC-DS') {
                    sh '''
                        mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
                          -Dsonar.projectKey=Employee-Management-System \
                          -Dsonar.projectName=Employee-Management-System \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }
        stage('Sonar Quality Gate') {
            steps {
                script {
                  waitForQualityGate abortPipeline: false, credentialsId: 'sonarqube-cred-d' 
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Build & Push Multi-Arch Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'dockerhub-cred-d') {
                        // sh '''
                        //   # Create and use a buildx builder with docker-container driver
                        //   docker buildx create \
                        //     --name jenkins-multiarch \
                        //     --driver docker-container \
                        //     --use || docker buildx use jenkins-multiarch
        
                        //   docker buildx inspect --bootstrap
        
                        //   docker buildx build \
                        //     --platform linux/amd64,linux/arm64 \
                        //     -t dipanshushukla/employee-management-system:latest-dev \
                        //     --push .
                        // '''

                      sh "docker buildx create --name mybuilder --use"
                      sh """docker buildx build \
                      --platform linux/amd64,linux/arm64 \
                      -t dipanshushukla/employee-management-system:latest-dev \
                      --push ."""
                   }
               }
            }
        }
        stage('Trivy Docker Image Scan') {
            steps {
                sh 'trivy image -f table -o trivy-ems-be-container-image-report.html dipanshushukla/employee-management-system:latest-dev && cat trivy-ems-be-container-image-report.html'
            }
        }
    }
}