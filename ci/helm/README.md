##### **Running a HELM file locally**

_prerequisites:_
- minikube
- helm (2.16.1)
- helmfile (https://github.com/roboll/helmfile)

Start minikube or any other kubernetes cluster.

Add a repository for your charts.
helm repo add backbase https://repo.backbase.com/backbase-charts --username myName --password myPassword
helm repo add backbase-charts https://repo.backbase.com/backbase-charts --username myName --password myPassword

username & password from Active Directory

##### **Running Helmfiles**

go to the directory of your helm file
Run the following commands.

```
helmfile --selector "tier=infrastructure" sync
helmfile --selector "tier=ips" sync
helmfile --selector "tier=cxs" sync
helmfile --selector "tier=system-tests" sync
```
Now system test can run.

To access the pods use:
```
kubectl port-forward gateway-pod-name 8080
```
Than you can go via the gateway to the different services
eg.: http://localhost:8080/api/targeting/actuator/health
