<%@ include file="../../templates/header.jsp" %>

    <main id="main" class="main">

    <div class="pagetitle">
        <h1>Cours de tous les cryptomonnaies</h1>
        <nav>
          <ol class="breadcrumb">
            <li class="breadcrumb-item"><a href="accueil.jsp">Accueil</a></li>
            <li class="breadcrumb-item">Cours</li>
          </ol>
        </nav>
      </div>
  
      
    <section class="section">
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Cours en temps réel</h5>
      
                    <!-- Table with hoverable rows -->
                    <table class="table table-hover">
                      <thead>
                        <tr>
                          <th scope="col">Cryptomonnaie</th>
                          <th scope="col">Cours actuel</th>
                          <th></th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td>Protoshare</td>
                          <td>3000</td>
                          <td><a href="coursDetail.jsp">Visualiser l'évolution ?</a></td>
                        </tr>
                        <tr>
                          <td>Bitcoin</td>
                          <td>3000</td>
                          <td><a href="coursDetail.jsp">Visualiser l'évolution ?</a></td>
                        </tr>
                        <tr>
                          <td>Dogecoin</td>
                          <td>3000</td>
                          <td><a href="coursDetail.jsp">Visualiser l'évolution ?</a></td>
                        </tr>
                        <tr>
                          <td>Ripple</td>
                          <td>3000</td>
                          <td><a href="coursDetail.jsp">Visualiser l'évolution ?</a></td>
                        </tr>
                        <tr>
                          <td>NxT Coin</td>
                          <td>3000</td>
                          <td><a href="coursDetail.jsp">Visualiser l'évolution ?</a></td>
                          </tr>
                      </tbody>
                    </table>
                    <!-- End Table with hoverable rows -->
      
                  </div>
            </div>
          </div>
        </div>
      </section>

  </main>

<%@ include file="../../templates/footer.jsp" %>
