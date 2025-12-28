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
      environment {
        SONAR_URL = "http://localhost:9000"
      }
      steps {
        withCredentials([string(credentialsId: 'sonarqube', variable: 'SONAR_AUTH_TOKEN')]) {
          sh 'mvn sonar:sonar -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.host.url=${SONAR_URL}'
        }
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
      environment {
        GIT_REPO_NAME = "CI-CD_implementation"
        GIT_USER_NAME = "3034saurabhkumar"
      }
      steps {
        withCredentials([usernamePassword(credentialsId: 'github-release-creds', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GITHUB_TOKEN'
        )]) {
          sh '''
            set -e

            cho "📥 Cloning deployment repository..."
            rm -rf deploy-repo
            git clone https://${GIT_USERNAME}:${GITHUB_TOKEN}@github.com/${GIT_USER_NAME}/${GIT_REPO_NAME}.git deploy-repo

            cd deploy-repo

            git config user.email "saurabhkumar3034@gmail.com"
            git config user.name "Saurabh Kumar"

            echo "📝 Updating deployment.yml"
            sed -i "s/replaceImageTag/${RELEASE_VERSION}/g" deployment.yml

            git add deployment.yml
            git commit -m "Update image tag to ${RELEASE_VERSION}" || echo "No changes to commit"

            git push origin main
          '''
        }
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