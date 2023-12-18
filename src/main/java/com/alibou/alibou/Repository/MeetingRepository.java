package com.alibou.alibou.Repository;

import com.alibou.alibou.Model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting , Integer> {
}
