<%@ include file="../../templates/header.jsp" %>

  <main id="main" class="main">

    <div class="pagetitle">
        <h1>Graphe</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="accueil.jsp">Accueil</a></li>
            <li class="breadcrumb-item"> <a href="cours.jsp">Cours</a></li>
            <li class="breadcrumb-item">Nom de la cryptomonnaie</li>
          </ol>
        </nav>
      </div>
  
      
      <section class="section">
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Graphe d'évolution graphique de "nom_cryptomonnaie"</h5>
      
                <!-- Area Chart -->
                <div id="areaChart"></div>
      
                <script>
                    document.addEventListener("DOMContentLoaded", () => {
                      let prices = [8107.85, 8128.0, 8122.9, 8165.5, 8340.7];
                      let timeInterval = 10; // 10 secondes d'intervalle
                      let currentTime = new Date();
                      
                      // Arrondir l'heure actuelle aux multiples de 10 secondes
                      currentTime.setSeconds(Math.floor(currentTime.getSeconds() / timeInterval) * timeInterval);
                      
                      let times = [];
                      
                      // Générer les 10 dernières valeurs de prix et leurs dates alignées tous les 10 secondes
                      for (let i = 0; i < prices.length; i++) {
                        // Calculer chaque timestamp à 10 secondes d'intervalle
                        times.push(new Date(currentTime.getTime() - (i * timeInterval * 1000)).getTime());
                      }
        
                      // Créer le graphique avec les données générées
                      new ApexCharts(document.querySelector("#areaChart"), {
                        series: [{
                          name: "Prix de la cryptomonnaie",
                          data: prices
                        }],
                        chart: {
                          type: 'area',
                          height: 350,
                          zoom: {
                            enabled: false
                          }
                        },
                        dataLabels: {
                          enabled: false
                        },
                        stroke: {
                          curve: 'smooth'
                        },
                        subtitle: {
                          text: 'Cours',
                          align: 'left'
                        },
                        labels: times,
                        xaxis: {
                          type: 'datetime',
                          labels: {
                            format: 'HH:mm:ss' // Format de l'heure
                          }
                        },
                        yaxis: {
                          opposite: true
                        },
                        legend: {
                          horizontalAlign: 'left'
                        }
                      }).render();
                    });
                  </script>
                <!-- End Area Chart -->
      
              </div>
            </div>
          </div>
        </div>
      </section>
      
      

  </main>

<%@ include file="../../templates/footer.jsp" %>
