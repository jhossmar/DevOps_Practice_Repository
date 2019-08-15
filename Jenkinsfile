pipeline{
    agent none
     parameters{
         string defaultValue: '', description: 'Nombre del Nodo  del ambiente QA,   ', name: 'LABEL_QA_NODE', trim: false
         string defaultValue: '', description: 'Nombre del Nodo  del ambiente PROD,   ', name: 'LABEL_PROD_NODE', trim: false
         string defaultValue: 'master', description: 'Nombre del Nodo  del ambiente MASTER,   ', name: 'LABEL_MASTER_NODE', trim: false

     }
    environment{
        QA_NODE="${params.LABEL_QA_NODE}"
        PROD_NODE="${params.LABEL_PROD_NODE}"
        MASTER_NODE="${params.LABEL_MASTER_NODE}"
    }
    stages{
        stage("Clone Repository"){
            agent { label MASTER_NODE }
            steps{
                git branch: 'final', url: 'https://github.com/jhossmar/DevOps_Practice_Repository.git'
                sh "echo Cloned!"
            }
        }
        stage("Prepare Docker image"){
            agent { label MASTER_NODE }
            steps{
                sh "tar -cvf DevOps_Practice_Repository.tar  ajax/ config/ db_sistema_mas_datos.sql db_sistema_Schema.sql files/ fpdf181/ index.php modelos/ public/ reportes/ vistas/"
                sh "docker build -t marcelo/final:v1 ."
                sh "docker save -o tienda.tar marcelo/final:v1"
                stash name: "stash-artifact", includes: "tienda.tar"
                archiveArtifacts 'tienda.tar'
            }
        }
        stage("Deployment on QA environment"){
            agent { label QA_NODE }
            steps{
                unstash "stash-artifact"
                sh "docker load -i tienda.tar"
                sh "docker rm tiendav1 -f || true"
                sh "docker run -idt -p 8081:80 --name tiendav1 marcelo/final:v1 /bin/bash -c 'service mysql start; service apache2 start; mysql -h localhost --user='root' --password='123456' < db_sistema_mas_datos.sql; bash'"
            }
        }
        stage("Run Automation tests"){
            agent { label QA_NODE}
            steps {
                sh "docker rm browser -f || true"
                //sh "docker run -d -p 4444:4444 --name browser --link tiendav1 selenium/standalone-chrome"
                sh "docker run -d -p 4444:4444 -p 5995:5900 -e VNC_NO_PASSWORD=1 --name browser --link tiendav1 selenium/standalone-chrome-debug:3.141.59-titanium"
                sh "mvn test"
            }
            
        }
         stage("Generate Automation report"){
            agent{label QA_NODE}
            steps{
                cucumber buildStatus: 'UNSTABLE',
                fileIncludePattern: 'target/*.json',
                trendsLimit: 10,
                classifications: [
                    [
                        'key': 'Browser',
                        'value': 'Chrome'
                    ]
                ]
            }
        }
        stage("Deploy in PROD environment"){
            agent { label MASTER_NODE}
            steps {
                sh "ansible-playbook playbook.yml"
                sh "echo 'DONE..'"
            }
            
        }
    }
}
