class Bank(val allowedAttempts: Integer = 3) {

    private val transactionsQueue: TransactionQueue = new TransactionQueue()
    private val processedTransactions: TransactionQueue = new TransactionQueue()

    def addTransactionToQueue(from: Account, 
                              to: Account, 
                              amount: Double): Unit = {
        // TODO
        // project task 2
        // create a new transaction object and put it in the queue
        // spawn a thread that calls processTransactions
        transactionsQueue.push(
            new Transaction(
                transactionsQueue, 
                processedTransactions, 
                from, 
                to, 
                amount, 
                allowedAttempts)
        )
        val thread = new Thread {
            override def run() = processTransactions
        }
        thread.start
    }
        

    private def processTransactions: Unit = {
        // TOO
        // project task 2
        // Function that pops a transaction from the queue
        // and spawns a thread to execute the transaction.
        // Finally do the appropriate thing, depending on whether
        // the transaction succeeded or not
        val transaction = transactionsQueue.pop

        val thread = new Thread {
            override def run() {
                transaction.run()
                if (transaction synchronized { transaction.status == TransactionStatus.PENDING }) {
                    transactionsQueue.push(transaction)
                    processTransactions
                } else {
                    processedTransactions.push(transaction)
                }
            }
        }
        thread.start
    }
    
    def addAccount(initialBalance: Double): Account = {
        new Account(this, initialBalance)
    }

    def getProcessedTransactionsAsList: List[Transaction] = {
        processedTransactions.iterator.toList
    }

}
