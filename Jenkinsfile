pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Show Java & Maven') {
      steps {
        bat 'java -version'
        bat 'mvn -v'
      }
    }

    stage('Compile') {
      steps {
        bat 'mvn -B clean compile'
      }
    }

    stage('Unit Tests') {
      steps {
        bat 'mvn -B test'
      }
      post {
        always {
          junit 'target\\surefire-reports\\*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        bat 'mvn -B package'
      }
      post {
        success {
          archiveArtifacts artifacts: 'target\\*.jar,target\\*.war', fingerprint: true
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv('SonarQube') {
          withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
            bat """
              mvn -B sonar:sonar ^
              -Dsonar.projectKey=SpringBootBestPracticesKit ^
              -Dsonar.projectName=SpringBootBestPracticesKit ^
              -Dsonar.host.url=http://localhost:9000 ^
              -Dsonar.login=%SONAR_TOKEN%
            """
          }
        }
      }
    }
  }
}
