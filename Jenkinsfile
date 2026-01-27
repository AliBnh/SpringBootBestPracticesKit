pipeline {
agent any


stages {
stage('Checkout') {
steps { checkout scm }
}


stage('Show Java & Maven') {
steps {
sh 'java -version || true'
sh 'mvn -v || true'
}
}


stage('Compile') {
steps { sh 'mvn -B clean compile' }
}


stage('Unit Tests') {
steps { sh 'mvn -B test' }
post {
always { junit 'target/surefire-reports/*.xml' }
}
}


stage('Package') {
steps { sh 'mvn -B package' }
post {
success { archiveArtifacts artifacts: 'target/*.jar,target/*.war', fingerprint: true }
}
}


stage('SonarQube Analysis') {
steps {
echo 'SonarQube étape désactivée (à activer après config).'
}
}
}
}
