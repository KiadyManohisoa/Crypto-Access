  <%@ include file="../../templates/header.jsp" %>
  
  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Section Vente</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="accueil.jsp">Accueil</a></li>
          <li class="breadcrumb-item"><a href="vente.jsp">Vente</a></li>
          <li class="breadcrumb-item">Protoshare</li>
        </ol>
      </nav>
    </div>

    <section class="section">
      <div class="row"><div class="container my-5">
        
        <div class="mb-4">
            <a href="vente.jsp" class="btn btn-outline-primary rounded-pill">
              <i class="bi bi-arrow-left me-2"></i> Retour
            </a>
          </div>

        <div class="row justify-content-center">
          <div class="col-lg-7">
            <div class="card shadow-sm">
              <!-- Titre de la cryptomonnaie -->
              <div class="card-header text-center bg-light text-white">
                <h4 class="card-title mb-0">Nom de la Cryptomonnaie</h4>
              </div>
              <!-- Corps de la carte -->
              <div class="card-body">
                <!-- Image d'aperçu -->
                <div class="mb-4 text-center">
                </div>
                <!-- Première ligne : Vendeur et Prix -->
                <div class="row mb-3">
                  <div class="col-6 text-muted text-start">
                    <small>Vendu par : <strong>Jean Pascal</strong></small>
                  </div>
                  <div class="col-6 text-end">
                    <h5>Prix unitaire : <span class="text-success">$50</span></h5>
                  </div>
                </div>
                <!-- Deuxième ligne : Quantité et Bouton Acheter -->
                <div class="row align-items-center">
                    <div class="col-6 d-flex align-items-center">
                    <label for="quantityInput" class="form-label mb-0 me-2">Quantité:</label>
                    <input type="number" id="quantityInput" class="form-control" min="1" placeholder="Entrez la quantité">
                    </div>
                    <div class="col-6 text-end">
                    <button type="button" class="btn btn-success rounded-pill px-4">Acheter</button>
                    </div>
                </div>
  
              </div>
            </div>
          </div>
        </div>
      </div>
      
          
      </div>
    </section>


  </main>

<%@ include file="../../templates/footer.jsp" %>