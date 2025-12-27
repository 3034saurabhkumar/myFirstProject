// def createReleaseBranch(version) {
//   // Define the name of the new release branch
//   def releaseBranchName = "release-${version}"
//   // Define the base branch from which to branch off
//   def baseBranch = "main"
//
//   echo "Checking out ${baseBranch} branch..."
//   // Checkout the base branch
//   sh "git checkout ${baseBranch}"
//   sh "git pull origin ${baseBranch}"
//
//   echo "Creating new release branch: ${releaseBranchName}"
//   // Create a new local branch
//   sh "git checkout -b ${releaseBranchName}"
//   // Push the new branch to the remote repository
//   sh "git push origin ${releaseBranchName}"
//   echo "Successfully created and pushed ${releaseBranchName}"
// }

pipeline {
  agent any

  environment {
    // Define your release branch name dynamically
    RELEASE_BRANCH = "release-${env.BUILD_NUMBER}"
    DOCKER_IMAGE = "my-app-image"
  }

  // Example parameter to pass the new version number
  // parameters {
  //   string(name: 'RELEASE_VERSION', defaultValue: '1.0.0', description: 'The version number for the new release')
  // }

  stages {
    stage('Checkout') {
      steps {
        // This step clones the repo into the workspace. Make sure to select
        // "None" for "Additional Behaviours" in the Jenkins job configuration
        // if you want to manage the branching manually with 'sh' steps.
        // checkout scm
        sh """
          git checkout -b ${RELEASE_BRANCH}
          git push origin ${RELEASE_BRANCH}
        """
        sh 'echo passed'
      }
    }

    // stage('Create Release Branch') {
    //   steps {
    //     script {
    //         // Call the function to create the branch using the parameter
    //         createReleaseBranch(params.RELEASE_VERSION)
    //     }
    //   }
    // }

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