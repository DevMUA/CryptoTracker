pipeline {
    environment { 
        registry = "http://192.168.160.48:5000" 
        accountAppImageName = "esp53/account-app"
        accountAppImage = ''
    }
    agent any 
    stages {
        stage("Cleaning Workspace") {
            steps {
                sh 'rm -rf .git'
                sh 'rm -rf .gitignore'
                sh 'rm -rf -v ./*'
            }
        }
        stage('Cloning from Git') {
            steps {
                sh 'git clone https://github.com/DevMUA/CryptoTracker.git .'
            }
        }
        stage('Build Maven Projects') {
            steps {
                sh '''
                cd accountModule/AccountApp
                java -version
                mvn -e -Dmaven.test.skip=true package
                '''
            }
        }
        stage('Building Images') { 
            steps {
                script {
                    docker.withRegistry(registry) {
                        accountAppImage = docker.build(accountAppImageName, "./accountModule/AccountApp")
                    }
                }
            }
        }
        stage('Publish Images') { 
            steps { 
                script { 
                    docker.withRegistry(registry) { 
                        accountAppImage.push()
                    }
                } 
            }
        }
        stage('Cleaning up local images') { 
            steps { 
                sh "docker rmi $accountAppImageName" 
            }
        }
        stage("Deploy to Playground VM") {
            steps {
                sh "echo deploying"
            }
        }
    }
}
