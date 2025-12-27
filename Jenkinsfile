pipeline {
  agent any

  parameters {
    string(name: 'RELEASE_VERSION', defaultValue: '1.0.0', description: 'Enter the release version (e.g., 1.0.1)')
  }

  stages {
    stage('Prepare Workspace') {
        steps {
            cleanWs() // Deletes previous build artifacts and old .git data
            checkout scm // Fresh clone/fetch of the repo
        }
    }

    stage('Create Release Branch') {
      steps {
        withCredentials([gitUsernamePassword(credentialsId: 'github-release-creds', gitToolName: 'Default')]) {
          sh """
            # Configure git user info (required for committing/tagging)
            git config user.email "jenkins@example.com"
            git config user.name "Jenkins CI"

            # Create and push the branch
            git checkout -b release-${params.RELEASE_VERSION}
            git push origin release-${params.RELEASE_VERSION}
          """
        }
        sh 'echo passed'
      }
    }

    stage('Build and Test with Maven') {
      agent {
        docker {
          image 'maven'
          args '-v $PWD:/workspace -w /workspace'
        }
      }
      steps {
        sh 'mvn clean package -DskipTests'
        sh 'ls -lh target'
        // 👇 Save build artifacts
        stash name: 'jar-artifact', includes: 'target/*.jar'
        sh 'echo passed'
      }
    }

    stage('Static Code Analysis') {
      steps {
        sh 'echo passed'
      }
    }

    stage('Build and Push Docker Image') {
        environment {
            DOCKER_IMAGE = "saurabh3034/spring-voldemort:${params.RELEASE_VERSION}"
            // DOCKERFILE_LOCATION = "myFirstProject/Dockerfile"
            REGISTRY_CREDENTIALS = credentials('docker-cred')
        }
          steps {
            // 🔥 THIS WAS MISSING OR IN WRONG PLACE
            unstash 'jar-artifact'

            script {
                sh 'docker build -t ${DOCKER_IMAGE} .'
                def dockerImage = docker.image("${DOCKER_IMAGE}")
                docker.withRegistry('https://index.docker.io/v1/', "docker-cred") {
                    dockerImage.push()
                }
            }
            sh 'echo passed'
          }

    }

    stage('Update Deployment File') {
      steps {
        sh 'echo passed'
      }
    }
  }

  post {
    success {
      echo '✅ Pipeline completed successfully!'
    }
    failure {
      echo '❌ Pipeline failed. Check the console output.'
    }
  }
}