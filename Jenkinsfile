pipeline {
  agent any

  parameters {
    string(name: 'RELEASE_VERSION', defaultValue: '1.0.0', description: 'Enter the release version (e.g., 1.0.1)')
  }

  stages {
    stage('Checkout & Create Release Branch') {
      steps {
        // This step clones the repo into the workspace. Make sure to select
        // "None" for "Additional Behaviours" in the Jenkins job configuration
        // if you want to manage the branching manually with 'sh' steps.
        // checkout scm
        withCredentials([gitUsernamePassword(credentialsId: 'github-relaese-creds', gitToolName: 'Default')]) {
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

    stage('Build and Test') {
      steps {
        sh 'echo passed'
      }
    }

    stage('Static Code Analysis') {
      steps {
        sh 'echo passed'
      }
    }

    stage('Build and Push Docker Image') {
      steps {
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
      echo 'Pipeline completed successfully!'
    }
    failure {
      echo 'Pipeline failed. Check the console output.'
    }
  }
}