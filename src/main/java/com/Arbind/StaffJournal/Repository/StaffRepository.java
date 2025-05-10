package com.Arbind.StaffJournal.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Arbind.StaffJournal.Entity.Staffs;


@Repository
public interface StaffRepository extends MongoRepository<Staffs , String>
{

}
