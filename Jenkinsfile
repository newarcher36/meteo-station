pipeline {

    agent any
    
    tools {
        maven 'maven 3.8.5'
        jdk 'jdk11'
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
        stage("build") {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
        stage("test") {
            steps {
                echo 'echo step'
            }
        }
        stage("deploy") {
            steps {
                echo 'deploy step'
            }
        }
    }
}