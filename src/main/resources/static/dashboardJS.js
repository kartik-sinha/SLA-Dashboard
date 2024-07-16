let count = 0;
let polylines = [];
let infoWindows = [];
let val = 0;
let displayMarkers = [];
let flag = false;
let device_lineid;
let highlightedMarker = null;

async function fetchSchema() {
    const response1 = await fetch('/schema');
    const text1 = await response1.text();
    const list = JSON.parse(text1);
    return list;
}

async function fetchConfig() {
    try {
        const response = await fetch('/properties');
        const text = await response.text();
        const config = JSON.parse(text);
        return config;
    } catch (error) {
        console.error('Error fetching config:', error);
        return {};
    }
}

async function fetchRoute(deviceID, map) {
    const response = await fetch(`/devices/${deviceID}`);
    const data = await response.json();
    const RouteCoordinates = [];

    data.forEach(location => {
        RouteCoordinates.push({ lat: location.latitude, lng: location.longitude });
        removePolylines();

        const devicePath = new google.maps.Polyline({
            path: RouteCoordinates,
            geodesic: false,
            strokeColor: "#FF0000",
            strokeOpacity: 1.0,
            strokeWeight: 2,
        });

        devicePath.setMap(map);
        polylines.push({ polyline: devicePath });
    });
}

async function fetchLocations(map) {
    try {
        const response = await fetch(`/devices`);
        const data = await response.json();

        if (data.length === 0) {
            console.error("No data received from /devices endpoint");
            return;
        }
        console.log(data);

        displayMarkers.forEach(marker => marker.setMap(null));
        displayMarkers = [];

        const deviceIdInput = document.getElementById('device-id-input').value.trim().toLowerCase();

        data.forEach(location => {
            const marker = new google.maps.Marker({
                position: { lat: location.latitude, lng: location.longitude },
                map: map,
                title: location.name,
                label: location.device_Id.replace('Device ', '')
            });

            displayMarkers.push(marker);

            if (deviceIdInput && location.device_Id.toLowerCase().includes(deviceIdInput)) {
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/purple-dot.png');
                marker.setLabel(null)
                if (highlightedMarker && highlightedMarker !== marker) {
                    highlightedMarker.setIcon(null);
                    highlightedMarker.setLabel(highlightedMarker.getLabel()); // Reset the label
                }
                highlightedMarker = marker;
            }

            marker.addListener('click', function () {
                if (highlightedMarker && highlightedMarker !== marker) {
                    highlightedMarker.setIcon(null);
                    highlightedMarker.setLabel(highlightedMarker.getLabel()); // Reset the label
                }
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/purple-dot.png');
                highlightedMarker = marker;

                if (flag === false) {
                    flag = true;
                    device_lineid = location.device_Id;
                    fetchRoute(device_lineid.replace("Device ", ""), map);
                } else {
                    flag = false;
                    removePolylines();
                }

                openFloatingPanel(location);
            });
        });
    } catch (error) {
        console.error('Error fetching locations:', error);
    }
}

async function initMap() {
    const response = await fetch('/devices');
    const data = await response.json();

    if (data.length === 0) {
        console.error("No data received from /devices endpoint");
        return;
    }

    const map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: { lat: data[0].latitude, lng: data[0].longitude },
        fullscreenControl: false,
        mapId: '2bb76ea8d1bd2716'
    });

    const buttons = [
        ["Rotate Left", "rotate", 20, google.maps.ControlPosition.LEFT_CENTER],
        ["Rotate Right", "rotate", -20, google.maps.ControlPosition.RIGHT_CENTER],
        ["Tilt Down", "tilt", 20, google.maps.ControlPosition.TOP_CENTER],
        ["Tilt Up", "tilt", -20, google.maps.ControlPosition.BOTTOM_CENTER],
    ];

    buttons.forEach(([text, mode, amount, position]) => {
        const controlDiv = document.createElement("div");
        const controlUI = document.createElement("button");

        controlUI.classList.add("ui-button");
        controlUI.innerText = text;
        controlUI.addEventListener("click", () => {
            adjustMap(mode, amount);
        });
        controlDiv.appendChild(controlUI);
        map.controls[position].push(controlDiv);
    });

    const adjustMap = function (mode, amount) {
        switch (mode) {
            case "tilt":
                map.setTilt(map.getTilt() + amount);
                break;
            case "rotate":
                map.setHeading(map.getHeading() + amount);
                break;
            default:
                break;
        }
    };

    infoWindow = new google.maps.InfoWindow();

    const locationButton = document.createElement("button");
    locationButton.textContent = "Pan to Current Location";
    locationButton.classList.add("custom-map-control-button");
    map.controls[google.maps.ControlPosition.TOP_CENTER].push(locationButton);
    locationButton.addEventListener("click", () => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const pos = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude,
                    };

                    infoWindow.setPosition(pos);
                    infoWindow.setContent("Location found.");
                    infoWindow.open(map);
                    map.setCenter(pos);
                },
                () => {
                    handleLocationError(true, infoWindow, map.getCenter());
                }
            );
        } else {
            handleLocationError(false, infoWindow, map.getCenter());
        }
    });

    mapcode = setInterval(() => {
        fetchLocations(map);
    }, 2000);
}

async function openFloatingPanel(location) {
    const lp = document.getElementById('left-panel');
    const ts = document.getElementById('top-section');
    const bs = document.getElementById('bottom-section');

    lp.style.display = 'block';
    lp.style.width = '250px';
    lp.style.height = '175px';

    ts.innerHTML = `
        <span>Device ID: ${location.device_Id}</span>
        <a href="javascript:void(0)" id="cross1" class="closebtn" onclick="closeFloatingPanel()">&times;</a>
    `;

    bs.innerHTML = `
        <div>
            <h3>${location.device_Id}</h3>
            <p>Latitude: ${location.latitude}</p>
            <p>Longitude: ${location.longitude}</p>
            <button onclick="openSidePanel('${location.device_Id}', ${location.latitude}, ${location.longitude})">More Info</button>
        </div>
    `;

    document.getElementById('cross1').addEventListener('click', function () {
        removePolylines();
        clearInterval(code2);
        flag = false;
        closeSidePanel();
    });
}

function closeFloatingPanel() {
    const ts = document.getElementById("top-section");
    const bs = document.getElementById('bottom-section');
    const fp = document.getElementById('left-panel');

    fp.style.display = 'none';
    fp.style.width = '0px';
    fp.style.height = '0px';

    ts.innerHTML = ``;
    bs.innerHTML = ``;
}

async function openSidePanel(deviceId, lat, lon) {
    val++;
    count++;
    if (count > 1) {
        clearInterval(code);
        count--;
    }

    const config = await fetchConfig();
    const interval = config.interval;
    const sidePanelWidth = config.sidePanelWidth;
    const initialPoint = config.initialPoint;
    const finalPoint = config.finalPoint;

    deviceId = deviceId.replace("Device ", "");
    const sP = document.getElementById("side-panel");
    sP.style.width = sidePanelWidth;
    sP.innerHTML = `
        <a href="javascript:void(0)" id="cross" class="closebtn" onclick="closeSidePanel()">&times;</a>
        <p>Device ID: ${deviceId}</p>`;
    const size = finalPoint - initialPoint;
    for (let i = 1; i <= size; i++) {
        sP.innerHTML += `<p>Humidity: <span id="h${i}"></span> &nbsp;&nbsp;&nbsp;&nbsp; Temperature: <span id="t${i}"></span></p>`;
    }

    const lis = await fetchSchema();

    code = setInterval(function () {
        fetch(`/devicedata/${deviceId}`)
            .then(response => response.json())
            .then(data => {
                const hum = lis[2].toLowerCase();
                const temp = lis[3].toLowerCase();

                const latestData = data.reverse().slice(initialPoint, finalPoint);

                for (let i = 1; i <= size; i++) {
                    const hElement = document.getElementById(`h${i}`);
                    const tElement = document.getElementById(`t${i}`);

                    if (latestData[i - 1]) {
                        hElement.innerText = latestData[i - 1][hum];
                        tElement.innerText = latestData[i - 1][temp];
                    } else {
                        hElement.innerText = '';
                        tElement.innerText = '';
                    }
                }
            })
            .catch(error => console.error('Error fetching device data:', error));
    }, interval);
}

function closeSidePanel() {
    clearInterval(code);
    document.getElementById("side-panel").innerHTML = ``;
    document.getElementById("side-panel").style.width = "0";
}

function closeSidePanelWithoutInterval() {
    document.getElementById("side-panel").innerHTML = ``;
    document.getElementById("side-panel").style.width = "0";
}

function removePolylines() {
    polylines.forEach(item => {
        item.polyline.setMap(null);
    });
}
