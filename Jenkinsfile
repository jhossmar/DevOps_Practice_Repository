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
                sh "docker run -idt -p 8081:80 --name tiendav1 marcelo/final:v1 /bin/bash -c 'service mysql start; service apache2 start; mysql -h localhost --user='root' --password='123456' < db_sistema_mas_datos.sql; bash'"
            }
        }
        stage('Change to branch automation testin'){
             agent { label 'Machine_virtual_osboxes.org' }
             steps{
	      sh "git pull origin automation"
              sh "git checkout automation"
                
            
             }
        }
         stage("Run Automation tests"){
            agent { label 'Machine_virtual_osboxes.org'}
            steps {
                sh "docker rm browser -f || true"
                //sh "docker run -d -p 4444:4444 --name browser --link tiendav1 selenium/standalone-chrome"
                sh "docker run -d -p 4444:4444 -p 5995:5900 -e VNC_NO_PASSWORD=1 --name browser --link tiendav1 selenium/standalone-chrome-debug:3.141.59-titanium"
                sh "mvn test"
            }
            
        }
         stage("Generate Automation report"){
            agent{label "Machine_virtual_osboxes.org"}
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
    }
}
