pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Show Java & Maven') {
      steps {
        bat 'java -version'
        bat 'mvn -v'
      }
    }

    stage('Build & Tests (with Coverage)') {
      steps {
        // verify => compile + tests + génération rapport jacoco (si plugin jacoco bien configuré)
        bat 'mvn -B clean verify'
      }
      post {
        always {
          // résultats des tests
          junit 'target\\surefire-reports\\*.xml'

          // archive le rapport JaCoCo (utile comme preuve)
          archiveArtifacts artifacts: 'target\\site\\jacoco\\**', allowEmptyArchive: true
        }
      }
    }

    stage('Package') {
      steps {
        // Optionnel: si verify a déjà fait le packaging, on peut juste repackager sans retester
        // (évite de relancer les tests une 2e fois)
        bat 'mvn -B -DskipTests package'
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
              -Dsonar.login=%SONAR_TOKEN% ^
              -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
            """
          }
        }
      }
    }
  }

  post {
    always {
      echo "Pipeline finished."
    }
  }
}
