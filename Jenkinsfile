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
        stage('checkout') {
            steps{
                git 'https://github.com/seniquel/gestion-des-missions-back.git'
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
                if (${env.BRANCH_NAME} == 'master')
                    echo '${env.JOB_NAME} Success ${env.JOB_NAME} ${currentBuild.DisplayName} ${currentBuild.absoluteUrl} (Léo)'   
                    // discordSend description: 'Success ${currentBuild.DisplayName} ${currentBuild.absoluteUrl}', 
                    //     footer: 'Léo est le meilleur', image: '', link: '', result: 'SUCCESS', thumbnail: '',
                    //     title: ' ${env.JOB_NAME}', 
                    //     webhookURL: 'https://discordapp.com/api/webhooks/747819422705778738/dHWPHidlNLpiiKftWU84__Ss2LAkws77Swfdk5OWs22qla3hlI1B4zywW8ROg4nAwjRM'
                    // slackSend channel: 'jenkins-training', color: 'good', 
                    //     message: 'Success ${env.JOB_NAME} ${currentBuild.DisplayName} ${currentBuild.absoluteUrl} (Léo)', 
                    //     tokenCredentialId: 'slack-token', teamDomain: 'devinstitut'
            }
        }

            
        }
        failure {
            echo '${env.JOB_NAME} Success ${env.JOB_NAME} ${currentBuild.DisplayName} ${currentBuild.absoluteUrl} (Léo)'
                // discordSend description: 'Failure ${currentBuild.DisplayName} ${currentBuild.absoluteUrl}', 
                //     footer: 'Léo a lamentablement échoué', image: '', link: '', result: 'FAILURE', thumbnail: '',
                //     title: ' ${env.JOB_NAME}', 
                //     webhookURL: 'https://discordapp.com/api/webhooks/747819422705778738/dHWPHidlNLpiiKftWU84__Ss2LAkws77Swfdk5OWs22qla3hlI1B4zywW8ROg4nAwjRM'
                // slackSend channel: 'jenkins-training', color: 'danger', 
                //     message: 'Failure ${env.JOB_NAME} ${currentBuild.DisplayName} ${currentBuild.absoluteUrl} (Léo)', 
                //     tokenCredentialId: 'slack-token', teamDomain: 'devinstitut'
        }
    }
}