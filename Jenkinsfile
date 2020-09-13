pipeline {
    agent any
    tools {
        maven 'Maven 3.6.3'
        jdk 'openjdk-11'
    }
    stages {
        stage('verify') {
            steps {
                sh 'mvn -v'
            }
        }
        stage('compile') {
            steps {
                sh 'mvn clean package'
            }
            post {
                failure {
                     discordSend description: "${env.GIT_COMMIT}", footer: "#${env.BUILD_NUMBER} - Failure", image: '', link: "${env.BUILD_URL}", result: 'FAILURE', thumbnail: "", title: "${env.JOB_NAME}", webhookURL: "${env.WEBHOOK_URL}"
                }
                success {
                    script {
                        if ("${env.BRANCH_NAME}" == 'master')
                            discordSend description: "${env.GIT_COMMIT}", footer: "#${env.BUILD_NUMBER} - Success", image: '', link: "${env.BUILD_URL}", result: 'SUCCESS', thumbnail: "", title: "${env.JOB_NAME}", webhookURL: "${env.WEBHOOK_URL}"
                    }
                }
            }
        }
    }
}