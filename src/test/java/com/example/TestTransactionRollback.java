// package com.example;
// import static org.junit.Assert.assertThrows;
// import com.example.transaction.TransactionManager;

// import org.junit.Test;

// public class TestTransactionRollback {

//     @Test
//     public void testTransactionRollback() {
//     TransactionManager transactionManager = new TransactionManager(
//         "jdbc:postgresql://localhost:5432/test_db",
//         "postgres",
//         "password"
//     );

//     // Simuler une transaction qui échoue
//     assertThrows(RuntimeException.class, () -> {
//         transactionManager.executeInTransaction(() -> {
//             throw new RuntimeException("Simulated failure");
//         });
//     });

//     // Vérifier que la base de données n'est pas modifiée
//     // (Par exemple, vérifier que les lignes insérées sont absentes)
// }
// }
