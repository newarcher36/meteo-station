pipeline {

    agent any

    tools {
        maven 'maven 3.8.5'
    }

    stages {
        stage('Initialize') {
            steps {
                sh '''
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage("build-test_meteo-station") {
            steps {
                sh 'echo "Compiling and launching unit (and in future also integration test)"'
                sh 'mvn clean install'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                    junit 'target/failsafe-reports/**/*.xml'
                }
            }
        }
        stage("build-docker-image") {
            steps {
                sh 'pwd'
                sh 'echo "Building docker image of meteo-station"'
                sh 'docker build -t newarcher/meteo-station:latest .'
            }
        }
        stage("publish-docker-image") {
            steps {
                sh 'echo "Pushing image newarcher/meteo-station:latest to docker hub"'
                sh 'cat /home/ec2-user/dpsx | docker login --username newarcher --password-stdin'
                sh 'docker push newarcher/meteo-station:latest'
                sh 'docker logout'
                sh 'echo "done!"'
            }
        }
    }
}