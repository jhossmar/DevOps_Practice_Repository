pipeline{
    agent none
    stages{
        stage("Clone Repository"){
            agent { label 'master' }
            steps{
                git branch: 'final', url: 'https://github.com/jhossmar/DevOps_Practice_Repository.git'
                sh "echo Cloned!"
            }
        }
        stage("Prepare Docker image"){
            agent { label ' master' }
            steps{
                sh "docker build -t marcelo/final:v1 ."
                sh "docker save -o tienda.tar marcelo/final:v1"
                stash name: "stash-artifact", includes: "tienda.tar"
                archiveArtifacts 'tienda.tar'
            }
        }
        stage("Deployment on QA environment"){
            agent { label 'Machine_virtual_osboxes.org' }
            steps{
                unstash "stash-artifact"
                sh "docker load -i tienda.tar"
                sh "docker rm tiendav1 -f || true"
                sh "docker run -idt -p 8080:80 --name tiendav1 marcelo/final:v1 /bin/bash -c 'service mysql start; service apache2 start; mysql -h localhost --user='root' --password='123456' < db_sistema_mas_datos.sql; bash'"
            }
        }
    }
}