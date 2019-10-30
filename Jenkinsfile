pipeline {
  agent any
  stages {
    stage('error') {
      steps {
        sh 'mvn -DskipTests=true clean install'
      }
    }
  }
}