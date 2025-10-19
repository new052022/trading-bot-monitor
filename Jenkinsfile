pipeline {
    environment {
        registry = "moritz007/trading-monitor"
        registryCredential = 'docker-hub-credentials'
    }
    tools {
        jdk 'JDK 21'
    }
    agent {
        label 'built-in'
    }
    stages {
        stage('Clean old containers') {
            steps {
                script {
                    sh '''
                    echo "Stopping and removing old containers..."
                    docker ps -a --filter ancestor=moritz007/trading-monitor --format "{{.ID}}" | xargs --no-run-if-empty docker stop || true
                    docker ps -a --filter ancestor=moritz007/trading-monitor --format "{{.ID}}" | xargs --no-run-if-empty docker rm -f || true

                    echo "Removing old images..."
                    docker images --filter reference=moritz007/trading-monitor --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f || true
                    '''
                }
            }
        }

        stage('Cloning our Git') {
            steps {
                 git branch: 'develop',
                     credentialsId: 'c1574c72-7536-44f0-b6a5-d0727c235306',
                     url: 'https://github.com/new052022trading-bot-monitor.git'
             }
        }

        stage('Building the application') {
            steps {
                script {
                      sh 'chmod +x gradlew && ./gradlew bootBuildImage'
                }
            }
        }

        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build("${registry}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Pushing the image to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy our image') {
            steps {
                script {
                    withEnv([
                        "POSTGRES_USER=${env.POSTGRES_USER}",
                        "POSTGRES_PASS=${env.POSTGRES_PASS}",
                        "DB_HOST=${env.DB_HOST}",
                        "DB_NAME=${env.DB_NAME}",
                         "QUANT_URL=${env.QUANT_URL}",
                         "JWT_SECRET=${env.JWT_SECRET}"
                    ]) {
                        sh '''
                        echo "Stopping and removing previous container..."
                        docker ps -f name=trading-monitor -q | xargs --no-run-if-empty docker stop || true
                        docker ps -a -f name=trading-monitor -q | xargs --no-run-if-empty docker rm -f || true

                        echo "Deploying new container..."
                        docker run -d --name trading-monitor \
                            --network moritz-network \
                            -p 8012:8012 \
                            -e POSTGRES_USER="$POSTGRES_USER" \
                            -e POSTGRES_PASS="$POSTGRES_PASS" \
                            -e DB_HOST="$DB_HOST" \
                            -e DB_NAME="$DB_NAME" \
                            -e QUANT_URL="$QUANT_URL" \
                            -e JWT_SECRET="$JWT_SECRET" \
                            ${registry}:${BUILD_NUMBER}
                        '''
                    }
                }
            }
        }

        stage('Cleaning up') {
            steps {
                script {
                    sh '''
                    echo "Cleaning up old images..."
                    docker images --filter reference=moritz007/trading-monitor --format "{{.ID}}" | xargs --no-run-if-empty docker rmi -f || true
                    '''
                }
            }
        }
    }
}
