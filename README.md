# Spring Boot Starter Dynamic Search

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=nickngn_spring-boot-starter-dynamic-search&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=nickngn_spring-boot-starter-dynamic-search)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=nickngn_spring-boot-starter-dynamic-search&metric=coverage)](https://sonarcloud.io/summary/new_code?id=nickngn_spring-boot-starter-dynamic-search)

## What is this?

This is the fast-food set up for Dynamic Search (aka. Advanced Search) for Spring Boot, built on top of Spring Data Jpa.

This provides typical settings for Criteria Search / Specification usages & validation supports.

Instead of boilerplate codes, what you need are:

Controller:

```java title=SearchController.java
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping("/search")
    public ResponseEntity<Page<SearchEntity>> search(@RequestBody SearchCriteria criteria) {
        return ResponseEntity.ok(searchService.search(criteria));
    }
}
```

Service:

```java title=SearchService.java
@Service
@RequiredArgsConstructor
public class SearchService {

    private final EntityRepository entityRepository;
    private final SpecBuilder<SearchEntity> builder = SpecBuilders.getInstance(SearchEntity.class);

    public Page<SearchEntity> search(SearchCriteria searchCriteria) {
        Specification<SearchEntity> spec = builder.build(searchCriteria.getCriteria());
        return entityRepository.findAll(spec, searchCriteria.getPageable());
    }
}
```

Repository:

```java
@Repository
public interface EntityRepository extends JpaRepository<SearchEntity, Long>,
        JpaSpecificationExecutor<SearchEntity> {
}
```

That's it! Everything is on your table.

### What else this can do?

- Validate syntax for key/values using Jakarta/Javax annotations
- Generate SQL conditions in WHERE clause.