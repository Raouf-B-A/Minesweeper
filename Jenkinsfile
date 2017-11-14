node() {
  stage('Checkout'){
	git url: 'https://github.com/Raouf-B-A/Minesweeper'
  }
  stage('Compile and Unit Tests'){
	sh "mvn clean package"
  }
  stage('Publish'){
	sh "mvn install"
  }
  stage('Quality Test'){
	sh "mvn sonar:sonar"
  }
  stage('Acceptance Test'){
	input id: 'deploy-stage', message: 'Deploy to Staging ?', ok: 'Deploy', submitter: 'jpbriend'
	deploy('acc-test')
  }
  stage('Prod'){
	input id: 'deploy-prod', message: 'Deploy to Production ?', ok: 'Deploy', submitter: 'jpbriend'
	deploy('acc-test')
	deploy('prod')
  }
}