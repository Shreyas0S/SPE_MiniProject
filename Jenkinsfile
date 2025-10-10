pipeline {
  agent any
  triggers { githubPush() }

  environment {
    DOCKER_IMAGE_NAME = 'scientific_calculator'
    DOCKERHUB_REPO    = 'shreyas0s'                 // DockerHub namespace
    DOCKERHUB_CRED    = 'DockerHubCred'         // credentialsId (username/password)
    GIT_REPO_URL      = 'https://github.com/Shreyas0S/SPE_MiniProject.git'
    GIT_BRANCH        = 'main'
    EMAIL_RECIPIENTS  = 'shreyas080105@gmail.com'       // comma-separated list; override in Jenkins or here
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: "${GIT_BRANCH}", url: "${GIT_REPO_URL}"
      }
    }

    stage('Build & Test (Maven)') {
      steps {
        script {
          // Clean, compile and run JUnit 5 tests for Calculator.java
          sh 'mvn -B -V clean package'
        }
      }
      post {
        always {
          // Publish JUnit test results even if tests fail
          junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
        }
        success {
          archiveArtifacts artifacts: 'target/calculator-*.jar', onlyIfSuccessful: true
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker --version'
        script {
          // Derive project version from the built jar name in a shell-safe way (no fragile escaping)
          def version = sh(returnStdout: true, script: '''
            set -e
            jar=$(ls target/calculator-*.jar 2>/dev/null | head -n1 || true)
            if [ -z "$jar" ]; then
              echo "" # will fallback to 'latest'
            else
              jar=${jar##*/}            # strip path
              ver=${jar#calculator-}    # drop prefix
              ver=${ver%.jar}           # drop suffix
              echo "$ver"
            fi
          ''').trim()
          if (!version) { version = 'latest' }
          sh "docker build --build-arg JAR_FILE=target/calculator-${version}.jar -t ${DOCKERHUB_REPO}/${DOCKER_IMAGE_NAME}:${version} -t ${DOCKERHUB_REPO}/${DOCKER_IMAGE_NAME}:latest ."
        }
      }
    }

    stage('Push Docker Image') {
      steps {
        // login using Jenkins credentials and push
        withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CRED}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
          sh '''
            echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
            docker push ${DOCKERHUB_REPO}/${DOCKER_IMAGE_NAME}:latest
          '''
        }
      }
    }

    stage('Run Ansible Playbook') {
      steps {
        ansiblePlaybook(playbook: 'deploy.yml', inventory: 'inventory')
      }
    }
  }

  post {
    success {
      script {
        def subject = "SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        def body = """
Build succeeded\n\nJob: ${env.JOB_NAME}\nBuild: #${env.BUILD_NUMBER}\nBranch: ${env.GIT_BRANCH}\nResult: ${currentBuild.currentResult}\nURL: ${env.BUILD_URL}\n"""
        try {
          emailext(to: env.EMAIL_RECIPIENTS, subject: subject, body: body, attachLog: true)
        } catch (err) {
          mail(to: env.EMAIL_RECIPIENTS, subject: subject, body: body)
        }
      }
    }
    failure {
      script {
        def subject = "FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
        def body = """
Build failed\n\nJob: ${env.JOB_NAME}\nBuild: #${env.BUILD_NUMBER}\nBranch: ${env.GIT_BRANCH}\nResult: ${currentBuild.currentResult}\nURL: ${env.BUILD_URL}\n\nCheck console log for details."""
        try {
          emailext(to: env.EMAIL_RECIPIENTS, subject: subject, body: body, attachLog: true)
        } catch (err) {
          mail(to: env.EMAIL_RECIPIENTS, subject: subject, body: body)
        }
      }
    }
    always { cleanWs() }
  }
}
