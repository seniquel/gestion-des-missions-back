pipeline {
    agent any
    	tools {
		// Install the Maven version configured as "M3" and add it to the path.
		maven "maven3.6.3"
    }
    stages {
		stage('verify') {
			steps{
				sh 'mvn -v'
			}
		}
        stage('build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
    post {
        success {
            script {
                if ("${env.BRANCH_NAME}" == 'master')
                    discordSend description: "Success ${env.GIT_COMMIT} ${env.BUILD_URL}", 
                        footer: 'Léo est le meilleur', image: '', link: '', result: 'SUCCESS', thumbnail: '',
                        title: "${env.JOB_NAME}", 
                        webhookURL: "${env.URL_DISCORD_FORMATION}"
                    slackSend channel: 'jenkins-training', color: 'good', 
                        message: "Success ${env.JOB_NAME} ${env.GIT_COMMIT} ${env.BUILD_URL} (Léo)", 
                        tokenCredentialId: 'slack-token', teamDomain: 'devinstitut'
            }
        }
        failure {
            discordSend description: "Failure ${env.GIT_COMMIT} ${env.BUILD_URL}", 
                footer: 'Léo a lamentablement échoué', image: '', link: '', result: 'FAILURE', thumbnail: '',
               title: "${env.JOB_NAME}", 
                 webhookURL: "${env.URL_DISCORD_FORMATION}"
             slackSend channel: 'jenkins-training', color: 'danger', 
                 message: "Failure ${env.JOB_NAME} ${env.GIT_COMMIT} ${env.BUILD_URL} (Léo)",
                tokenCredentialId: 'slack-token', teamDomain: 'devinstitut'
        }
    }
}