Feature: Workload service

  Scenario: Workload
    Given  Workload Trainer
    When Add to workload
    Then  Workload should be returned

  Scenario: Update
    Given  Workload Update Trainer
    When Update workload
    Then  Updated Workload should be returned

