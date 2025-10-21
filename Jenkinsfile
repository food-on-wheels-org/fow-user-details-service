pipeline {
    agent any /* pipeline can run on any available agent in Jenkins environment */

    environment {
        /* We set this up in the Jenkins initially */
        DOCKER_HUB_CREDENTIALS = credentials('DOCKERHUB_CREDENTIAL')
        VERSION = "${env.BUILD_ID}"
    }

    tools {
        /* We installed this in our Jenkins dashboard */
        maven "Maven"
    }

    /* Now coming to the stages of the pipeline */
    stages {

        stage('Maven Build') {
            steps {
                sh 'mvn clean package -DskipTests'
                /*  We are skipping the tests because we need to see if there are compilation errors first
                    Reason is that if there are compilation errors, we shuoldn't waste time running the tests after that.
                    Tests are run separately in the next stage */
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps{
                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install sonar:sonar -Dsonar.host.url=http://35.180.152.18:9000/ -Dsonar.login=squ_0d5f8aef120abca5b084ad40d3d55e0493c84733'
            }
        }

        stage('Code Coverage') {
            steps{
                script{
                    def token = "squ_0d5f8aef120abca5b084ad40d3d55e0493c84733"
                    def sonarQubeUrl = "http://35.180.152.18:9000/api"
                    /* The componentKey will always be a combination of groupId-artifactId */
                    def componentKey = "com.project.tejas:userdetails"
                    def coverageThreshold = 80.0

                    /* Log into SonarQube with Sonar token and URL, then run the coverage */
                    def response = sh(
                        script: """curl -H "Authorization: Bearer ${token}" "${sonarQubeUrl}/measures/component?component=${componentKey}&metricKeys=coverage" """,
                        returnStdout: true
                    ).trim()

                    def coverage = sh(
                        script: "echo '${response}' | jq -r '.component.measures[0].value'",
                        returnStdout: true
                    ).trim().toDouble()

                    echo: "Coverage: ${coverage}"

                    if(coverage < coverageThreshold){
                        error "Coverage is below the threshold of ${coverageThreshold}%. Aborting the pipeline."
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                sh 'echo ${DOCKER_HUB_CREDENTIALS_PSW} | docker login -u ${DOCKER_HUB_CREDENTIALS_USR} --password-stdin'
                sh 'docker build -t tejassrivathsa/user-details-service:${VERSION} .'
                sh 'docker push tejassrivathsa/user-details-service:${VERSION}'
            }
        }

        /* Cleaning up directory because by now, we have pushed the image to Dockerhub, so the job of the cloned repo is done */
        stage('Cleanup Workspace') {
            steps {
                deleteDir()
            }
        }

        /* Then we need to update the image version in our manifest files */
        stage('Update Image tag in GitOps') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[ credentialsId: 'git-ssh', url: 'git@github.com:food-on-wheels-org/fow-infra.git']])
                script {
                       sh '''
                          sed -i "s/image:.*/image: tejassrivathsa\\/user-details:${VERSION}/" k8s/apps/user-details-manifest.yml
                        '''
                          sh 'git checkout master'
                          sh 'git add .'
                          sh 'git commit -m "Update image tag"'
                        sshagent(['git-ssh'])
                            {
                                sh('git push')
                            }
                }
            }
        }
    }
}