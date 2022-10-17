function health {
    local up_check="{\"status\":\"UP\"}"
    local counter=0
    local response=0
    local limit=100
    local timeout=10

    local serviceId
    local checkType="$1"

    #Variables for url health check
    local targetUrl
    local serviceName

    #Variables for registry check

    local eurekaRegistryUrl

    if [[ "$checkType" == "eureka-registration" ]]; then
        local eurekaRegistryUrl="$2"

        if [[ -z "$2" || -z "$3" ]]; then
                echo "Error, wrong arguments for eureka-registration. Usage: health 'eureka-registration' 'eurekaAppsUrl' 'serviceName' "
                exit 1
        fi

        local serviceId="Service $3"
        local serviceName="$3"
    else
        if [[ -z "$2" ]]; then
            echo "Error, wrong arguments for $1. Usage: health 'serviceUrl'"
            exit 1
        fi

        local targetUrl="$2"
        local serviceId="Url $targetUrl"
    fi

    while [[ ${counter} -lt "$limit" ]]
    do
        counter=$((counter + 1))

        case $checkType in
            ping)
                echo "Pinging $targetUrl try $counter of $limit"
                response=$(curl -L --write-out "%{http_code}" --silent --output /dev/null --max-time ${timeout} --connect-timeout ${timeout} "$targetUrl")
                if [[ "$response" == "200" ]] ; then
                    break
                fi
                ;;
            health)
                echo "Checking health of $targetUrl try $counter of $limit"
                response=$(curl -L --write-out "%{http_code}" --silent --output /dev/null --max-time ${timeout} --connect-timeout ${timeout} "$targetUrl")
                if [[ "$response" == "200" ]] ; then
                    break
                fi
                ;;
            eureka-registration)
                echo "Checking eureka-registration of service $serviceName try $counter of $limit"
                response=$(curl -L --silent --max-time ${timeout} --connect-timeout ${timeout} "$eurekaRegistryUrl")
                extractedHealthUrl=$(echo "${response}" | grep "${serviceName}" | grep healthCheckUrl)
                if [[ ! -z "$extractedHealthUrl" ]]; then
                    response="200"
                    break
                fi
                ;;
            *)
                echo "Invalid option[$checkType] supplied for health function..."
                exit 1
        esac

        sleep ${timeout}
    done

    if [[ "$response" != "200" ]] && [[ "$response" != *"${up_check}"* ]]; then
        echo "$serviceId is down after $counter tries"
        exit 1
    fi
}

health "$@"
