pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat(script: 'mvn -DskipTests=true clean install', label: 'Maven', returnStdout: true)
      }
    }
    stage('Verify') {
      steps {
        bat(script: 'mvn verify sonar:sonar -Dsonar.projectKey=threesixty-finance -Dsonar.organization=markash-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=13a2641b2801291cbda4bc4d1e10b531fa726e29', label: 'SonarQube', returnStdout: true)
      }
    }
  }
}