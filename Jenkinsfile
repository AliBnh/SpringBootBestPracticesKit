pipeline {
  agent any

  tools {
    jdk 'JDK17'
  }

  environment {
    // SONARQUBE_SERVER = 'SonarQube'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Compile') {
      steps {
        sh 'mvn -B clean compile'
      }
    }

    stage('Unit Tests') {
      steps {
        sh 'mvn -B test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        sh 'mvn -B package -DskipTests=false'
      }
      post {
        success {
          archiveArtifacts artifacts: 'target/*.jar,target/*.war', fingerprint: true
        }
      }
    }

    stage('SonarQube Analysis') {
      when { expression { fileExists('pom.xml') } }
      steps {
        // withSonarQubeEnv("${SONARQUBE_SERVER}") {
        //   sh 'mvn -B sonar:sonar'
        // }
        echo 'SonarQube étape prête (à activer après config Sonar).'
      }
    }
  }

  post {
    always {
      echo 'Pipeline terminé.'
    }
  }
}
