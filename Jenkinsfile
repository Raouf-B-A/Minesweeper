node() {
  // Étape 1
  stage('etapes-1') {
    parallel(
      'etape-1.1': {
        echo "Job 1 de l'étape 1"
      },
      'etape-1.2': {
        echo "Job 2 de l'étape 1"
      },
    )
  }
  // Étape 2
  stage('etapes-2') {
    echo "Job 1 de l'étape 2"
  }
  // Étape 3
  stage('etapes-3') {
    parallel(
      'etape-3.1': {
        // Cette opération doit prendre moins de 10 secondes
        timeout(time: 10, unit: 'SECONDS') {
          echo "Job 1 de l'étape 3"
        }
      },
      'etape-3.2': {
        echo "Job 2 de l'étape 3"
      },
      'etape-3.3': {
        echo "Job 3 de l'étape 3"
      },
    )
  }
}