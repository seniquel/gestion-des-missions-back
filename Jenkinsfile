pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
    }
    stages {
        stage('verify') {
            steps {
                sh 'mvn -v'
            }
        }
        stage('compile') {
            when {
                not {
                    branch 'master'
                }
            }
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                     discordSend description: "${env.GIT_COMMIT}", footer: "#${env.BUILD_NUMBER} - Test Failure", image: '', link: "${env.BUILD_URL}", result: 'FAILURE', thumbnail: "", title: "${env.JOB_NAME}, ${env.GIT_BRANCH}", webhookURL: "${env.WEBHOOK_URL}"
                }
            }
        }
        stage('compile-master') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn clean package'
            }
            post {
                failure {
                     discordSend description: "${env.GIT_COMMIT}", footer: "#${env.BUILD_NUMBER} - Failure", image: '', link: "${env.BUILD_URL}", result: 'FAILURE', thumbnail: "", title: "${env.JOB_NAME}, ${env.GIT_BRANCH}", webhookURL: "${env.WEBHOOK_URL}"
                }
                success {
                    discordSend description: "${env.GIT_COMMIT}", footer: "#${env.BUILD_NUMBER} - Success", image: '', link: "${env.BUILD_URL}", result: 'SUCCESS', thumbnail: "", title: "${env.JOB_NAME}", webhookURL: "${env.WEBHOOK_URL}"
                }
            }
        }
    }
}