pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat(script: 'mvn -DskipTests=true clean install', label: 'Maven', returnStdout: true)
      }
    }
    stage('Verify') {
      agent any
      steps {
        bat(script: 'mvn verify sonar:sonar -Dsonar.projectKey=threesixty-finance -Dsonar.organization=markash-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=${env.SONAR_PWD}', label: 'SonarQube', returnStdout: true)
      }
    }
  }
}