package buruiana.calcfin.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Espressione {
    private String inputExpr;
    private ArrayList tokensExpr;
    private ArrayList validTokensList;
    private ArrayList rpnExpression;
    private Frazione risultato;

    public Espressione(String inputExpr) {
        this.inputExpr = inputExpr;
        tokensExpr = new ArrayList();
        validTokensList = new ArrayList();
        rpnExpression = new ArrayList();
    }

    public ArrayList getTokensExpr() {
        return tokensExpr;
    }

    public void setTokensExpr(ArrayList tokensExpr) {
        this.tokensExpr = tokensExpr;
    }

    public ArrayList getRpnExpression() {
        return rpnExpression;
    }

    public String getInputExpr() {
        return inputExpr;
    }

    public Frazione getRisultato() {
        return risultato;
    }

    public ArrayList getValidTokensList() {
        return validTokensList;
    }

    /**
     * Risolve l'espressione eseguendo tutti i passaggi richiesti
     */
    public void risolvi() throws EspressioneException, ArithmeticException {
        //da inputExpr a tokenList
        scanner();
        //da tokenList a validTokenList
        parser();
        //da valid TokenList a RPNExpr
        toRPN();
        //da RPNExpr a risultato
        calcRPN();
    }

    /**
     * Metodo statico per risolvere l'espressione passata in input
     * @param stringInput L'espressione da risolvere
     * @return Il risultato dell'espressione
     * @throws EspressioneException Se l'espressione non è scritta in forma corretta
     * @throws ArithmeticException In caso di operazioni non definite
     */
    public static Frazione risolvi(String stringInput) throws EspressioneException, ArithmeticException{
        Espressione espressione = new Espressione(stringInput);
        //da inputExpr a tokenList
        espressione.scanner();
        //da tokenList a validTokenList
        espressione.parser();
        //da valid TokenList a RPNExpr
        espressione.toRPN();
        //da RPNExpr a risultato
        System.out.println(espressione.getRpnExpression());

        espressione.calcRPN();
        return espressione.getRisultato();
    }


    public void scanner() throws EspressioneException{
        long numeratore = 0;
        int contaParentesi = 0;
        int posizione = 0;
        boolean inLetturaNumero = false;
        for (char c : inputExpr.toCharArray()) {
            switch (c) {
                case '+', '-', '*', '/', '^':
                    if (inLetturaNumero)
                        tokensExpr.add(new Frazione(numeratore, 1));
                    tokensExpr.add(Operatore.getOperatore(c));
                    inLetturaNumero = false;
                    break;
                case '(', ')':
                    if (inLetturaNumero)
                        tokensExpr.add(new Frazione(numeratore, 1));
                    tokensExpr.add(Parentesi.getParentesi(c));
                    if ((c == Parentesi.PARENTESI_APERTA.getSimbolo()))
                        contaParentesi++;
                    else {
                        contaParentesi--;
                        if (contaParentesi < 0)
                            throw new EspressioneException(
                                    "Espressione non valida",
                                    "L'espressione contiene parentesi non bilanciate in posizione " + posizione
                            );
                    }
                    inLetturaNumero = false;
                    break;
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0':
                    if(inLetturaNumero){
                        numeratore = 10 * numeratore + Integer.valueOf(Character.toString(c));
                    }else{
                        numeratore = Integer.valueOf(Character.toString(c));
                        inLetturaNumero = true;
                    }
                    break;
                case ' ':
                    //non considero gli spazi
                    break;
                default:
                    throw new EspressioneException(
                            "Carattere non valido",
                            "L'espressione contiene un carattere non valido in posizione " + posizione
                    );
            }
            posizione++;
        }

        if (inLetturaNumero)
            tokensExpr.add(new Frazione(numeratore, 1));

        if (contaParentesi != 0)
            throw new EspressioneException(
                    "Espressione non valida",
                    "L'espressione contiene parentesi non bilanciate"
            );
    }


    public void toRPN(){
        Deque<Object> stackOperatori = new ArrayDeque<>();
        for(Object token: validTokensList){
            if(token instanceof Frazione ){
                rpnExpression.add(token);
            } else if(token instanceof Operatore){
                while(!stackOperatori.isEmpty() && stackOperatori.peek() instanceof Operatore){
                    if(((Operatore) stackOperatori.peek()).getPriority() > ((Operatore) token).getPriority() || (((Operatore) stackOperatori.peek()).getPriority() == ((Operatore) token).getPriority() && ((Operatore) token).isLeftAssociative())){
                        rpnExpression.add(stackOperatori.pop());
                    }else{
                        break;
                    }
                }
                stackOperatori.push(token);
            } else if(token.equals(Parentesi.PARENTESI_APERTA)){
                stackOperatori.push(token);
            }else if(token.equals(Parentesi.PARENTESI_CHIUSA)){
                while(stackOperatori.peek() != Parentesi.PARENTESI_APERTA){
                    rpnExpression.add(stackOperatori.pop());
                }
                stackOperatori.pop();
            }
        }
        //svuoto lo stack operatori
        for(Object ob: stackOperatori){
            rpnExpression.add(stackOperatori.pop());
        }
    }

    /**
     * Esegue l'analisi sintattica dell'espressione tokenizzata
     */
    public void parser() throws EspressioneException {
        /*
            stato = 0: in attesa di espressione
            stato = 1: letto Operatore
            stato = 2: letto Operando (Frazione)
            stato = 3: letta Parentesi Chiusa
         */
        int stato = 0;
        for (Object token : tokensExpr) {
            switch (stato) {
                case 0:
                    /*-- stato 0 ----- in attesa di espressione -------------------------------*/
                    if (token instanceof Operatore) {
                        throw new EspressioneException("Espressione non valida ", token + " un operatore non può essere il primo elemento.");
                    } else if (token instanceof Parentesi) {
                        if(token == Parentesi.PARENTESI_APERTA){
                            validTokensList.add(token);
                        }else{
                            throw new EspressioneException("Espressione non valida ", token + "Non puoi iniziare con una parentesi chiusa.");
                        }
                    } else if (token instanceof Frazione) {
                        validTokensList.add(token);
                        stato = 2;
                    }

                    break;
                case 1:
                    /*-- stato 1 ----- letto Operatore -----------------------------*/
                    if (token instanceof Operatore) {
                        throw new EspressioneException(
                                "Espressione non valida",
                                token + " non può seguire un altro operatore");
                    } else if (token instanceof Frazione) {
                        validTokensList.add(token);
                        stato = 2;
                    } else if (token instanceof Parentesi) {
                        if(token == Parentesi.PARENTESI_APERTA){
                            validTokensList.add(token);
                        }else{
                            throw new EspressioneException("Espressione non valida ", token + "Non puoi mettere una parentesi chiusa.");
                        }
                    }
                    break;
                case 2:
                    /*-- stato 2 ----- letto Operando (Frazione) -----------------------------------------*/
                    if (token instanceof Operatore) {
                        validTokensList.add(token);
                        stato = 1;
                    } else if (token instanceof Frazione) {
                        throw new EspressioneException(
                                "Espressione non valida",
                                token + " non può seguire un operando");
                    } else if (token instanceof Parentesi) {
                        if(token == Parentesi.PARENTESI_CHIUSA){
                            validTokensList.add(token);
                        }else{
                            throw new EspressioneException("Espressione non valida ", token + "Non puoi iniziare mettere una parentesi aperta.");
                        }
                        stato = 3;
                    }
                    break;
                case 3:
                    /*-- stato 3 ----- letta Parentesi Chiusa ---------------------------------------------*/
                    if (token instanceof Operatore) {
                        validTokensList.add(token);
                        stato = 1;
                    } else if (token instanceof Frazione) {
                        throw new EspressioneException(
                                "Espressione non valida",
                                token + " non può seguire una parentesi chiusa");
                    } else if (token instanceof Parentesi) {
                        if(token == Parentesi.PARENTESI_CHIUSA){
                            validTokensList.add(token);
                        }
                    }
            }
        }
        //non deve terminare con un operatore (stato 1)
        if (stato == 1)
            throw new EspressioneException(
                    "Espressione non valida",
                    "L'espressione termina non può terminare con " + tokensExpr.getLast());
    }
    public void calcRPN() {
        Frazione operando1, operando2, risultatoParziale = null;
        Deque<Frazione> stackOperandi = new ArrayDeque<>();
        //Stack<Frazione> stackOperandi = new Stack<>();
        for (Object token : rpnExpression) {
            if (token instanceof Frazione) {
                //aggiungo l'operatore allo stackOperandi
                stackOperandi.push((Frazione) token);
            } else {
                //si tratta di un operatore...
                //tolgo l'elemento in cima allo stackOperandi e lo assegno a operando2
                operando2 = stackOperandi.pop();
                //tolgo l'elemento in cima allo stackOperandi e lo assegno a operando1
                operando1 = stackOperandi.pop();
                //eseguo l'operazione operando1 operatore operando2:
                try {
                    switch ((Operatore) token) {
                        case Operatore.ADD:
                            risultatoParziale = operando1.sum(operando2);
                            break;
                        case Operatore.SUB:
                            risultatoParziale = operando1.sub(operando2);
                            break;
                        case Operatore.MULT:
                            risultatoParziale = operando1.mult(operando2);
                            break;
                        case Operatore.DIV:
                            risultatoParziale = operando1.div(operando2);
                            break;
                        case Operatore.POW:
                            risultatoParziale = operando1.pow(operando2);
                            break;
                    }
                } catch (Exception ex) {
                    throw ex;
                }
                //aggiungo il risultato allo stack operandi
                stackOperandi.push(risultatoParziale);
            }
        }
        this.risultato = stackOperandi.pop();
    }
}
