pipeline {
    environment { 
        registry = "http://192.168.160.48:5000" 
        accountAppImageName = "esp53/account-app"
        frontEndImageName = "esp53/frontend"
        mlModuleAPIImageName = "esp53/mlmodule-api"
        mlModuleKafkaImageName = "esp53/mlmodule-kafka"
        accountAppImage = ''
        frontEndImage = ''
        mlModuleAPIImage = ''
        mlModuleKafkaImage = ''
    }
    agent any 
    stages {
        stage("Build Maven Projects") {
            agent {
                docker { image "maven:3.8.1-openjdk-15" }
            }
            steps {
                sh '''
                cd accountModule/AccountApp
                java -version
                mvn -Dmaven.test.skip=true package
                '''
                stash includes: '**/target/*.jar', name: 'app'
            }
        }

        stage("Test Maven Projects") {
            agent {
                docker { image "maven:3.8.1-openjdk-15" }
            }
            steps {
                sh '''
                cd accountModule/AccountApp
                java -version
                mvn -Dmaven.test.failure.ignore=true test
                '''
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage("Build Images") {
            steps {
                unstash 'app'
                script {
                    docker.withRegistry(registry) {
                        accountAppImage = docker.build(accountAppImageName, "./accountModule/AccountApp")
                        frontEndImage = docker.build(frontEndImageName, "./frontend")
                        mlModuleAPIImage = docker.build(mlModuleAPIImageName, "./mlModule/model_api")
                        mlModuleKafkaImage = docker.build(mlModuleKafkaImageName, "./mlModule/model_kafka")
                    }
                }
            }
        }

        stage('Publish Images') { 
            steps { 
                script { 
                    docker.withRegistry(registry) { 
                        accountAppImage.push()
                        frontEndImage.push()
                        mlModuleAPIImage.push()
                        mlModuleKafkaImage.push()
                    }
                } 
            }
        }

        stage('Cleaning up local images') { 
            steps { 
                sh "docker rmi $accountAppImageName"
                sh "docker rmi $frontendImageName" 
                sh "docker rmi $mlModuleAPIImageName"
                sh "docker rmi $mlModuleKafkaImageName"
            }
        }
        
        stage("Deploy to Playground VM") {
            steps {
                sh "echo deploying"
            }
        }
    }
}
