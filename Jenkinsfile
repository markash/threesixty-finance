pipeline {
  agent any
  stages {
    stage('error') {
      steps {
        bat(script: 'mvn -DskipTests=true clean install', label: 'Maven', returnStdout: true)
      }
    }
  }
}