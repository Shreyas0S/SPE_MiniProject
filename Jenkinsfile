pipeline {
  agent any
  triggers { githubPush() }

  environment {
    DOCKER_IMAGE_NAME = 'scientific_calculator'
    DOCKERHUB_REPO    = 'shreyas0s'                 // DockerHub namespace
    DOCKERHUB_CRED    = 'DockerHubCred'         // credentialsId (username/password)
    GIT_REPO_URL      = 'https://github.com/Shreyas0S/Test_MiniProject.git'
    GIT_BRANCH        = 'main'
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
          // Derive project version from the built jar name (or from pom via Maven exec if needed)
          def version = sh(returnStdout: true, script: "ls target | grep '^calculator-.*SNAPSHOT.jar$' | sed 's/calculator-\\(.*\\)\.jar/\\1/'").trim()
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
    always { cleanWs() }
  }
}
