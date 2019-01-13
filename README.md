# Match API
## Running the API locally

### Tools
- Java: `brew cask install java`
- Intellj (needs JAVA_HOME variable declared in the system PATH)

## Running mongoDB locally

```
brew install mongodb
mkdir -p /data/db
sudo chown -R $USER /data/db
mongod
```

## Running MongoDB in Docker

### Using docker compose

```bash
docker-compose up
```

### Create docker containers individually

```bash
docker network create matchapimongo
docker run -d --name matchapimongodb --network=matchapimongo -v ~/matchapidb:/data/db mongo
docker exec -it CONTAINER_ID bash
```

```bash
docker build -t springboot-mongo:latest .
docker run -p 8080:8080 --name matchapispringbootmongo --network=matchapimongo springboot-mongo
```

### Useful commands:

```bash
docker start -ai ${CONTAINER_ID}
```
```bash
docker rm -f $(docker ps -a -q)
```
```bash
docker rmi -f $(docker images -a -q)
```
```bash
docker volume rm $(docker volume ls -q)
```
```bash
docker network rm $(docker network ls | tail -n+2 | awk '{if($2 !~ /bridge|none|host/){ print $1 }}')
```

## Running Match API in kubernetes locally

Tools required:
* [Kubernetes command line tool](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
* [Minikube](https://kubernetes.io/docs/tasks/tools/install-minikube/)
* [VirtualBox](https://www.code2bits.com/how-to-install-virtualbox-on-macos-using-homebrew/) -- Needed by Minikube
* [Kompose](https://kubernetes.io/docs/tasks/configure-pod-container/translate-compose-kubernetes/)

Before starting kubernetes will try to pull the docker images from the docker hub repository by default. We need to upload our springboot docker image to the docker hub repository. To do so you just need an account that will give permissions to upload docker images.

When running the docker-compose command we see that it has generated docker images. Get the IMAGE ID of the generated docker image. For it you can list your images executing:
```bash
docker images -a
```

First you need to tag your image executing the following command:
```bash
docker tag ae41e009e71d dgomezdeveloper/match-api-springboot-service:firsttry
```

To push the docker image execute:
```bash
docker push dgomezdeveloper/match-api-springboot-service
```

To be able to deploy our docker containers to a Kubernetes cluster we need to translate our current `docker-compose.yaml` file onto kubernetes config yaml files.

For this purpose Kubernetes offer a tool called `Kompose` that will take the `docker-compose.yaml` as an input an generate different kubernetes yaml files.

To generate the files execute:
```bash
$ kompose convert
WARN Unsupported root level networks key - ignoring
WARN Unsupported networks key - ignoring          
WARN Unsupported depends_on key - ignoring        
WARN Volume mount on the host "~/matchapidb" isn't supported - ignoring path on the host
INFO Kubernetes file "matchapispringbootmongo-service.yaml" created
INFO Kubernetes file "matchapimongodb-deployment.yaml" created
INFO Kubernetes file "matchapimongodb-claim0-persistentvolumeclaim.yaml" created
INFO Kubernetes file "matchapispringbootmongo-deployment.yaml" created
```
As you see in the logs, the `networks` key is not supported. We will come back to that later.

Since the network was not created, this means that the containers won't see each other. For the springboot application to be able to see the mongo db we should create a kubernetes service over the mongo DB that exposes it within our kubernetes cluster.

The mongo db service `yaml` should look like this:

```
apiVersion: v1
kind: Service
metadata:
  name: matchapimongodb
  labels:
    app: matchapimongodb
spec:
  ports:
  - port: 27017
    protocol: TCP
  selector:
    appdb: matchapimongodb
```

The next step is to start a kubernetes cluster in our machine. `Minikube` with help us with this. Execute the following:
```bash
$ minikube start
Starting local Kubernetes cluster...
Running pre-create checks...
Creating machine...
Starting local Kubernetes cluster...
```
If you would like to see how your kubernetes cluster looks like by executing the following, the kubernetes dashboard will be opened in your default browser:
```bash
$ minikube dashboard
Opening http://127.0.0.1:50202/api/v1/namespaces/kube-system/services/http:kubernetes-dashboard:/proxy/ in your default browser...
```

Now we have the files which we will user to deploy our containers in kubernetes. The next step is to deploy them using the following command specifying all the generated `yaml` files as input:
```bash
$ kubectl create -f matchapimongodb-claim0-persistentvolumeclaim.yaml,matchapispringbootmongo-service.yaml,matchapimongodb-service.yaml,matchapimongodb-deployment.yaml,matchapispringbootmongo-deployment.yaml
persistentvolumeclaim/matchapimongodb-claim0 created
service/matchapispringbootmongo created
service/matchapimongodb created
deployment.extensions/matchapimongodb created
deployment.extensions/matchapispringbootmongo created
```
If you get an error saying that the docker images could not be pulled, you can login using the following command entering your credentials:
```bash
docker login
```

If you refresh your dashboard now you should be able to see your services, volumes and deployments.

The IP that should be used to execute the REST API request is show by typing:
```
$ minikube ip
192.168.99.100
```

Executing the following command we will see the port that is exposing the springboot service:
```
$ kubectl get services
NAME                      TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes                ClusterIP   10.96.0.1       <none>        443/TCP          59s
matchapimongodb           ClusterIP   10.105.86.54    <none>        27017/TCP        54s
matchapispringbootmongo   NodePort    10.106.105.81   <none>        8080:30004/TCP   54s
```

Using this IP `192.168.99.100` and the port specified for the springboot container `30004` , we can check if our Match API is up and running executing:

```
$ curl -X GET http://192.168.99.100:30004/matches
```

If you want to delete the whole cluster, just type:
```
minikube stop
minikube delete
```

If you want to delete services manually, you can use the kubernetes command:

```
kubectl delete --all services
```

If you want to check the virtualbox minikube image you can user the command:
```
docker-machine ls
```

If you want to create a new one because the current one is corrupted, you can use the following comand:
```
docker-machine create
```

## Running Match API in kubernetes in AWS

Tools required:

* [AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html)
* [Kubernetes command line tool](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
* [KOPS](https://github.com/kubernetes/kops#installing)

Firstly, we should create or reuse an existing IAM and grant the following permissions:

```
AmazonEC2FullAccess
AmazonRoute53FullAccess
AmazonS3FullAccess
AmazonVPCFullAccess
```

The next step is to configure our aws cli providing the Access Key, Secret Key and the AWS region in which you want the Kubernetes cluster to be installed:

```
$ aws configure

AWS Access Key ID [None]: AccessKeyValue
AWS Secret Access Key [None]: SecretAccessKeyValue
Default region name [None]: eu-centralt-1
Default output format [None]:
```

The default output format option can be specified with an empty value.

We need to create an S3 bucket for `kops` to store the state of the Kubernetes cluster and its configuration. Using the aws cli we can create a bucket executing:

```
aws s3api create-bucket --bucket match-api-kops-store --region ue-central-1
```

Once the bucket is created we should enable versioning executing:
```
aws s3api put-bucket-versioning --bucket match-api-kops-store  --versioning-configuration Status=Enabled
```

The next step is to create our Kubernetes cluster. Before that, `kops` suggests to set two environment variables: `KOPS_CLUSTER_NAME` adn `KOPS_STATE_STORE`. This variables should be added to your `.bash_profile` or `.bashrc` files:

```
#kops - kubernetes
export KOPS_CLUSTER_NAME=matchapi.k8s.local
export KOPS_STATE_STORE=s3://match-api-kops-store
```

It is not mandatory but you defined them when creating the cluster using the command `kops create cluster ...`if the name of the Kubernetes cluster ends with `k8s.local`, Kubernetes will create a [gossip-based cluster](https://github.com/kubernetes/kops/blob/master/docs/aws.md#prepare-local-environment). This means during the creation of a `kops` cluster or a rolling update of a cluster, Etd nodes need to discover each other. Instead of using `Route53` for DNS, the hostnames are now updated via `protokube` container running gossip.

Now we are going to create the configuration of the Kubernetes cluster by executing:

```
kops create cluster --node-count=2 --node-size=t2.micro --zones=eu-central-1a
```

This command will also write into the S3 bucket specified this configuration. In this example we are creating 2 `t2.micro` EC2 work nodes and one

If the command worked you should see something like this in the console:

```
Cluster configuration has been created.

Suggestions:
 * list clusters with: kops get cluster
 * edit this cluster with: kops edit cluster matchapi.k8s.local
 * edit your node instance group: kops edit ig --name=matchapi.k8s.local nodes
 * edit your master instance group: kops edit ig --name=matchapi.k8s.local master-eu-central-1a

Finally configure your cluster with: kops update cluster matchapi.k8s.local --yes
```

The next step is to actually create our cluster. The following command will take some minutes until all EC2 instances are in running state:

```
 kops update cluster --name matchapi.k8s.local --yes
 ```

If the command worked, you should see something like this at the end of the logs:

```
Cluster is starting.  It should be ready in a few minutes.

Suggestions:
 * validate cluster: kops validate cluster
 * list nodes: kubectl get nodes --show-labels
 * ssh to the master: ssh -i ~/.ssh/id_rsa xxxx@api.matchapi.k8s.local
 * the admin user is specific to Debian. If not using Debian please use the appropriate user based on your OS.
 * read about installing addons at: https://github.com/kubernetes/kops/blob/master/docs/addons.md.
 ```

We should wait few minutes for the instances to be ready, you can either check the status opening your AWS console or typing:

```
kubectl get nodes
```

Once they are ready we need to validate the cluster:

```
kops validate cluster
```

If the command worked, you should see the following line at the end of the logs:

```
Your cluster matchapi.k8s.local is ready
```

At this point our AWS Kubernetes cluster is ready for deploying our app on it. Before we deploy our app we can run the Kubernetes Dashboard and check the cluster status. By the time this article was written the correct `yaml` file to be used is the following:

```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v1.10.1/src/deploy/recommended/kubernetes-dashboard.yaml
```

I strongly recommend you to store this `yaml` in your repository since it is changing quite frequently and sometimes it gets broken.

In the logs you will see that some items were created. To be able to open the dashboard we need to know the AWS hostname:

```
kubectl cluster-info

Kubernetes master is running at https://api-matchapi-k8s-local-vhuivv-448219637.eu-central-1.elb.amazonaws.com
KubeDNS is running at https://api-matchapi-k8s-local-vhuivv-448219637.eu-central-1.elb.amazonaws.com/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy

To further debug and diagnose cluster problems, use 'kubectl cluster-info dump'.
```

With the specified hostname you should be able to open the dasboard loading in your web browser the following URL:

```
https://api-matchapi-k8s-local-vhuivv-448219637.eu-central-1.elb.amazonaws.com/ui
```

At this point you should be prompted with a dialog asking for a username & password. The username is `admin` and the password is provided by executing the following command:

```
kops get secrets kube --type secret -oplaintext
```

Then, you will be prompted with another dialog offering two options `Kubeconfig` or `Token`. Select the `Token` option and introduce the value given by this command:

```
kops get secrets admin --type secret -oplaintext
```

After introducing the token you will be able to see your Dashboard.

If you where not able to follow this last two steps, you should check if the dashboard was created correctly:

```
kubectl get pods --all-namespaces --show-all
Flag --show-all has been deprecated, will be removed in an upcoming release
NAMESPACE     NAME                                                                    READY   STATUS             RESTARTS   AGE
kube-system   dns-controller-6d6b7f78b-6lvxv                                          1/1     Running            0          38m
kube-system   etcd-server-events-ip-172-20-34-64.eu-central-1.compute.internal        1/1     Running            0          38m
kube-system   etcd-server-ip-172-20-34-64.eu-central-1.compute.internal               1/1     Running            0          37m
kube-system   kube-apiserver-ip-172-20-34-64.eu-central-1.compute.internal            1/1     Running            2          38m
kube-system   kube-controller-manager-ip-172-20-34-64.eu-central-1.compute.internal   1/1     Running            0          36m
kube-system   kube-dns-5fbcb4d67b-ls6mf                                               3/3     Running            0          38m
kube-system   kube-dns-5fbcb4d67b-q8xtz                                               3/3     Running            0          36m
kube-system   kube-dns-autoscaler-6874c546dd-f27fk                                    1/1     Running            0          38m
kube-system   kube-proxy-ip-172-20-34-64.eu-central-1.compute.internal                1/1     Running            0          37m
kube-system   kube-proxy-ip-172-20-60-241.eu-central-1.compute.internal               1/1     Running            0          36m
kube-system   kube-proxy-ip-172-20-63-131.eu-central-1.compute.internal               1/1     Running            0          35m
kube-system   kube-scheduler-ip-172-20-34-64.eu-central-1.compute.internal            1/1     Running            0          37m
kube-system   kubernetes-dashboard-6b5d754444-bnt9h                                   0/1     CrashLoopBackOff   11         34m
```

If the dashboard pod status is `CrashLoopBackOff` probably there is something wrong with the `yaml` file used for creating the dashboard.

You can access the logs of an specific pod executing:
```
kubectl logs kubernetes-dashboard-6b5d754444-bnt9h -n kube-system --follow
standard_init_linux.go:178: exec user process caused "exec format error"
```

In my case this error was solved using the above specified `yaml` file.

Moreover, if you have problems loading the dashboard you can always access it via a proxy:

```
kubectl proxy

Starting to serve on 127.0.0.1:8001
```

Then, open your browser to http://localhost:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/.

The last step is to actually deploy our app in our AWS Kubernetes cluster. For this we just need to repeat the steps we did in our local deployment:

```
$ kubectl create -f matchapimongodb-claim0-persistentvolumeclaim.yaml,matchapispringbootmongo-service.yaml,matchapimongodb-deployment.yaml,matchapispringbootmongo-deployment.yaml,matchapimongodb-service.yaml
persistentvolumeclaim/matchapimongodb-claim0 created
service/matchapispringbootmongo created
deployment.extensions/matchapimongodb created
deployment.extensions/matchapispringbootmongo created
service/matchapimongodb created
```

We can check the status of our services executing:

```
$ kubectl get services
NAME                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
kubernetes                ClusterIP   100.64.0.1       <none>        443/TCP          19m
matchapimongodb           ClusterIP   100.66.191.212   <none>        27017/TCP        5m
matchapispringbootmongo   NodePort    100.67.101.131   <none>        8080:30831/TCP   5m
```

And we should also check our pods status either refreshing our DAshboard opened in our browser or typing:

```
$ kubectl get pods
NAME                                       READY   STATUS    RESTARTS   AGE
matchapimongodb-7c4f7459f8-lhz2l           1/1     Running   0          10m
matchapispringbootmongo-5897bc7474-ksmln   1/1     Running   0          10m
```
Finally, our services is running on an AWS cluster and in order to access it we just need to execute the folloswing request that will provide us an empty array of matches since we did not create any yet:

```
curl -X GET http://<Your EC2 instance public IP>:30831/matches
```

Note: the port specified matches with the one provided by the command `kubectl get services`.

If you would like to delete your AWS Kubernetes cluster, you can do it executing:

```
kops delete cluster --name ${KOPS_CLUSTER_NAME} --yes
```

Do not forget the `--yes` argument to delete the cluster. Otherwise, Kubernetes will perform a dry run without deleting the cluster.

if the command worked at the end of the logs you should see:

```
Deleted cluster: "matchapi.k8s.local"
```

## References

* [Hibernate with kotlin](https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/)
* [Accessing data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Dockerize a spring boot app with mongo DB](https://www.linkedin.com/pulse/dockerize-spring-boot-mongodb-application-aymen-kanzari/)
* [Pushing and Pulling to and from Docker Hub](https://ropenscilabs.github.io/r-docker-tutorial/04-Dockerhub.html)
* [Migrating a spring boot service to kubernetes in 5 steps](https://itnext.io/migrating-a-spring-boot-service-to-kubernetes-in-5-steps-7c1702da81b6)
* [Deploying Spring boot and MongoDB as containers using Kubernetes](https://dzone.com/articles/deploying-spring-boot-and-mongodb-as-containers-us-1)
* [Deployment setup for Spring Boot Apps with MongoDB and kubernetes](https://dzone.com/articles/a-developmentdeployment-setup-for-an-angular-sprin)
* [Running Spring boot applications with minikube](https://www.baeldung.com/spring-boot-minikube)
* [Setting up a Kubernetes Cluster on AWS in 5 minutes](https://ramhiser.com/post/2018-05-20-setting-up-a-kubernetes-cluster-on-aws-in-5-minutes/)

## Kahoot

* [From zero to Hero Quiz](https://play.kahoot.it/#/k/85b0b62a-2090-465e-869d-56578395156b)
