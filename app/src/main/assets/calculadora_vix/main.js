class App extends React.Component {

    constructor(props){
        super(props);

        this.state = {
            display: '0',
            operations: '',
            evalued: false
        }
    }

    handleNumber = (event) => {
        const number = event.target.textContent;

        if (this.state.operations.length >= 15) {
          return;
        }

        // introduce el número
        this.setState((state) => ({
            operations: state.operations + number,
            display: state.display === '0' || state.evalued === true ? number : state.display + number,
            evalued: false
        }));
    }

    // manejar la entrada de .
    handlePoint = (event) => {
        const point = event.target.textContent;

        const lastNumber = this.state.operations.split(' ').pop();

        if (this.state.operations.length >= 15) {
          return;
        }

        // dos puntos
        if (lastNumber.includes('.')) {
            return;
        }
        // normal
        this.setState((state) => ({
            operations: state.operations + point,
            display: state.display === '0' ? point : state.display + point,
            evalued: false
        }));
    }

    // manejar la entrada de operadores
    handleOperator = (event) => {
      // saca el operador introducido
      const operator = event.target.textContent;
      // caracter anterior
      const lastChat = this.state.operations.charAt(this.state.operations.length - 2);
      // comprueba si el caracter anterior es un operador
      const lastChatIsOperator = ["+", "*", "/", "-"].includes(lastChat);

      if (this.state.operations.length >= 15) {
          return;
      }

      // manejo de errores
      if (this.state.display === '') {
        this.setState(() => ({
          operations: 'SyntaxError',
          display: 'SyntaxError',
          evalued: false
        }));
        return;
      }

      //           condicion para el + -
      if (lastChatIsOperator && operator !== '-') {
        this.setState((state) => ({
          operations: `${state.operations.substring(0, state.operations.length - 2)}${operator} `,
          display: `${state.display.substring(0, state.display.length - 2)}${operator} `,
          evalued: false
        }));
        return;
      }

      // nada antes del operador
      if (this.state.operations === '') {
        this.setState((state) => ({
          operations: state.display + ' ' + operator + ' ',
          display: state.display === '0' ? '0' + ' ' + operator + ' ' : state.display + ' ' + operator + ' ',
          evalued: false
        }));
        return;
      }

      // normal
      this.setState((state) => ({
        operations: state.operations + ' ' + operator + ' ',
        display: state.display === '0' ? operator : state.display + ' ' + operator + ' ',
        evalued: false
      }));
    };

//   limpiar la pantalla
    handleClear = () => {
        this.setState({
            evalued: false,
            display: '0',
            operations: ''
        });
    }

  // hacer las operaciones
    handleEval = () => {

      if (this.state.operations) {
        const result = eval(this.state.operations);

        // canbiar el estado de evaluado
        this.setState(() => ({
            display: result.toString(),
            operations: '',
            evalued: true
        }));

        return;
      }

        // canbiar el estado de evaluado
        this.setState(() => ({
            display: '0',
            operations: '',
            evalued: true
        }));
    }

    render() {
        return(
            <article className="calculator">
                <div className="formulaScreen">{this.state.operations}</div>
                <div className="outputScreen" id="display">{this.state.display}</div>
                <React.Fragment>
                    <button key="clear" className="jumbo" id="clear" value="AC" onClick={this.handleClear}>AC</button>
                    <button key="divide" className="operator" id="divide" value="/" onClick={this.handleOperator}>/</button>
                    <button key="multiply" className="operator" id="multiply" value="*" onClick={this.handleOperator}>*</button>
                    <button key="seven" id="seven" value="7" onClick={this.handleNumber}>7</button>
                    <button key="eight" id="eight" value="8" onClick={this.handleNumber}>8</button>
                    <button key="nine" id="nine" value="9" onClick={this.handleNumber}>9</button>
                    <button key="subtract" className="operator" id="subtract" value="-" onClick={this.handleOperator}>-</button>
                    <button key="four" id="four" value="4" onClick={this.handleNumber}>4</button>
                    <button key="five" id="five" value="5" onClick={this.handleNumber}>5</button>
                    <button key="six" id="six" value="6" onClick={this.handleNumber}>6</button>
                    <button key="add" className="operator" id="add" value="+" onClick={this.handleOperator}>+</button>
                    <button key="one" id="one" value="1" onClick={this.handleNumber}>1</button>
                    <button key="two" id="two" value="2" onClick={this.handleNumber}>2</button>
                    <button key="three" id="three" value="3" onClick={this.handleNumber}>3</button>
                    <button key="zero" className="jumbo" id="zero" value="0" onClick={this.handleNumber}>0</button>
                    <button key="decimal" id="decimal" value="." onClick={this.handlePoint}>.</button>
                    <button key="equals" id="equals" value="=" onClick={this.handleEval}>=</button>
                </React.Fragment>
            </article>
        );
    }
}

const container = document.querySelector("#root");

const root = ReactDOM.createRoot(container);

root.render(
    <App/>
);