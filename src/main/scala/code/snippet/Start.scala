package code.snippet

class Start {

  def render() = {
    <html>
      <head>
        <meta content="text/html; charset=UTF-8" http-equiv="content-type"/>
        <title>Home</title>
        <script src="webjars/jquery/2.1.4/dist/jquery.min.js"></script>
      </head>
      <body>
        <div id="main">
          <h2>Welcome to your project!</h2>
          <hr/>
          <hr/>
          <div>
            Running in mode:<span data-lift="MyModeSnippet"> ... </span>
          </div>
          <hr/>
          <div>
            Static Time:<span data-lift="MyTimeSnippet"> ... </span>
          </div>
          <hr/>
          <div>
            Dynamic Time:<span data-lift="comet?type=MyTimeComet"> ... </span>
          </div>
          <hr/>
          <div>
            Dynamic Age of Person (reactive):
            <span data-lift="comet?type=MyReactiveComet">
              ...
            </span>
          </div>
          <hr/>
          <div>
            Person:<span data-lift="MyPersonSnippet"> ... </span>
          </div>
        </div>
        <button data-lift="MyButtonSnippet">Blub</button>
      </body>
    </html>
  }

}