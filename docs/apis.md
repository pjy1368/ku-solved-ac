## 2021.06.19 이후로 수정된 api 정리

1. https://solved.ac/api/v3/ranking/in_organization?organizationId={group_id}
- 특정 그룹의 유저 목록 가져오는 api.

2. https://solved.ac/api/v3/search/problem?query=solved_by:{user_id}
- 특정 유저가 푼 문제 가져오는 api.

3. https://solved.ac/api/v3/search/problem?query=
- 전체 문제 가져오는 api.

4. https://solved.ac/api/v3/search/problem?query=tier:{tier}
- 특정 티어에 해당하는 문제 가져오는 api.
- 티어는 unrated, b5, b4, ... , r1까지 있음.

주의) '&page=' 쿼리를 통해 페이지를 구분해야 함. 또한, 업데이트 이후 총 page 개수를 반환하지 않는데 한 페이지당 100개인 것을 알아둘 것.