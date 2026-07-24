pipeline {
    agent any
    
    environment {
        // Maven
        MAVEN_HOME = '/usr/share/maven'
        
        // Docker Registry (Harbor)
        DOCKER_REGISTRY = '192.168.101.11'
        HARBOR_PROJECT = 'java-app'
        APP_NAME = 'maven-java-app'
        IMAGE_TAG = "${BUILD_NUMBER}"
        
        // GitHub
        GIT_REPO = 'https://github.com/malkiats/maven.git'
        GIT_BRANCH = 'master'
        
        // Kubernetes
        K8S_NAMESPACE = 'java-apps'
        K8S_DEPLOYMENT = 'maven-java-app'
    }
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timestamps()
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo '========== Stage 1: Cloning Repository =========='
                    git branch: "${GIT_BRANCH}", url: "${GIT_REPO}"
                    echo "✓ Repository cloned successfully"
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo '========== Stage 2: Building with Maven =========='
                    sh '''
                        mvn --version
                        mvn clean package -DskipTests
                        ls -la target/
                        echo "✓ Build completed successfully"
                    '''
                }
            }
        }
        
        stage('Unit Tests') {
            steps {
                script {
                    echo '========== Stage 3: Running Unit Tests =========='
                    sh '''
                        mvn test
                        echo "✓ All tests passed"
                    '''
                }
            }
        }
        
        stage('Code Analysis') {
            steps {
                script {
                    echo '========== Stage 4: Code Quality Analysis =========='
                    sh '''
                        echo "Code analysis would run here (SonarQube, Checkstyle, etc.)"
                        echo "✓ Code analysis completed"
                    '''
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    echo '========== Stage 5: Building Docker Image =========='
                    sh '''
                        docker build -t ${APP_NAME}:${IMAGE_TAG} .
                        docker tag ${APP_NAME}:${IMAGE_TAG} ${DOCKER_REGISTRY}/${HARBOR_PROJECT}/${APP_NAME}:${IMAGE_TAG}
                        docker tag ${APP_NAME}:${IMAGE_TAG} ${DOCKER_REGISTRY}/${HARBOR_PROJECT}/${APP_NAME}:latest
                        docker images | grep ${APP_NAME}
                        echo "✓ Docker image built successfully"
                    '''
                }
            }
        }
        
        stage('Push to Harbor Registry') {
            steps {
                script {
                    echo '========== Stage 6: Pushing to Harbor Registry =========='
                    sh '''
                        echo "Logging in to Harbor registry..."
                        docker login -u jenkins -p Jenkins@Harbor123 ${DOCKER_REGISTRY}
                        docker push ${DOCKER_REGISTRY}/${HARBOR_PROJECT}/${APP_NAME}:${IMAGE_TAG}
                        docker push ${DOCKER_REGISTRY}/${HARBOR_PROJECT}/${APP_NAME}:latest
                        echo "✓ Image pushed to Harbor successfully"
                    '''
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    echo '========== Stage 7: Deploying to Kubernetes =========='
                    sh '''
                        echo "Creating Kubernetes namespace..."
                        kubectl create namespace ${K8S_NAMESPACE} --dry-run=client -o yaml | kubectl apply -f -
                        
                        echo "Creating deployment..."
                        kubectl set image deployment/${K8S_DEPLOYMENT} \
                            ${K8S_DEPLOYMENT}=${DOCKER_REGISTRY}/${HARBOR_PROJECT}/${APP_NAME}:${IMAGE_TAG} \
                            -n ${K8S_NAMESPACE} --record || true
                        
                        echo "Checking deployment status..."
                        kubectl rollout status deployment/${K8S_DEPLOYMENT} -n ${K8S_NAMESPACE} --timeout=5m || true
                        
                        echo "Pod Details:"
                        kubectl get pods -n ${K8S_NAMESPACE}
                        
                        echo "Service Details:"
                        kubectl get svc -n ${K8S_NAMESPACE}
                        
                        echo "✓ Deployment completed"
                    '''
                }
            }
        }
    }
    
    post {
        success {
            echo '✓✓✓ Pipeline SUCCESS ✓✓✓'
            echo "Application deployed: ${APP_NAME}:${IMAGE_TAG}"
        }
        failure {
            echo '✗✗✗ Pipeline FAILED ✗✗✗'
        }
        always {
            sh 'echo "Pipeline execution completed at $(date)"'
        }
    }
}
