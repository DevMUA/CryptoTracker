pipeline {
    environment { 
        registry = "http://192.168.160.48:5000" 
        accountAppImageName = "esp53/account-app"
        accountAppImage = ''
    }
    agent any 
    stages {
/*        stage("Cleaning Workspace") {
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
*/
        stage("Build Maven Projects") {
            agent {
                docker { image "maven:3.8.1-openjdk-15" }
            }
            steps {
                sh '''
                cd accountModule/AccountApp
                java -version
                mvn -e -Dmaven.test.failure.ignore=true package
                '''
                stash includes: '**/target/*.jar', name: 'app'
            }
        }

        stage("Build Images") {
            steps {
                unstash 'app'
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
