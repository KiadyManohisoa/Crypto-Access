<%@ include file="../../templates/header.jsp" %>
  

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Section portefeuille</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="accueil.jsp">Accueil</a></li>
          <li class="breadcrumb-item">Portefeuille</li>
        </ol>
      </nav>
    </div>

    <section class="section">
      <div class="row">
        <div class="col-lg-12">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Etat actuel de votre portefeuille</h5>

              <!-- Table with stripped rows -->
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th scope="col">Cryptomonnaie</th>
                    <th scope="col">Quantité</th>
                    <th scope="col">Prix Unitaire</th>
                    <th scope="col">Valeur</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>Protoshare</td>
                    <td>4</td>
                    <td>3000</td>
                    <td>12000</td>
                    <td><a href="#" class="sell-link" data-bs-toggle="modal" data-bs-target="#sellModal" data-crypto="Protoshare">Vendre ?</a></td>
                  </tr>
                  <tr>
                    <td>Bitcoin</td>
                    <td>4</td>
                    <td>3000</td>
                    <td>12000</td>
                    <td><a href="#" class="sell-link" data-bs-toggle="modal" data-bs-target="#sellModal" data-crypto="Bitcoin">Vendre ?</a></td>
                  </tr>
                  <tr>
                    <td>Dogecoin</td>
                    <td>4</td>
                    <td>3000</td>
                    <td>12000</td>
                    <td><a href="#" class="sell-link" data-bs-toggle="modal" data-bs-target="#sellModal" data-crypto="Dogecoin">Vendre ?</a></td>
                  </tr>
                  <tr>
                    <td>Ripple</td>
                    <td>4</td>
                    <td>3000</td>
                    <td>12000</td>
                    <td><a href="#" class="sell-link" data-bs-toggle="modal" data-bs-target="#sellModal" data-crypto="Ripple">Vendre ?</a></td>
                  </tr>
                  <tr>
                    <td>NxT Coin</td>
                    <td>4</td>
                    <td>3000</td>
                    <td>12000</td>
                    <td><a href="#" class="sell-link" data-bs-toggle="modal" data-bs-target="#sellModal" data-crypto="NxT Coin">Vendre ?</a></td>
                  </tr>
                </tbody>
                
              </table>
              <!-- End Table with stripped rows -->

            </div>
          </div>
        </div>
      </div>
    </section>

      <!-- Modal Vente -->
    <div class="modal fade" id="sellModal" tabindex="-1" aria-labelledby="sellModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="sellModalLabel">Vente de <span id="cryptoName"></span></h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <form id="sellForm">
              <div class="row mb-3">
                <label for="inputNumber" class="col-sm-2 col-form-label">Quantité</label>
                <div class="col-sm-10">
                  <input type="number" class="form-control" id="inputNumber" name="quantity" min="1" required>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annuler</button>
            <button type="submit" form="sellForm" class="btn btn-primary">Confirmer la vente</button>
          </div>
        </div>
      </div>
    </div>


  </main>

  <script src="../../assets/js/render/portefeuille.js"></script>


<%@ include file="../../templates/footer.jsp" %>
