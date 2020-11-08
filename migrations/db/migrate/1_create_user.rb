class CreateUser < ActiveRecord::Migration[5.2]
  def change
    create_table :users do |t|
      t.timestamp :created_at, null: true
      t.timestamp :updated_at, null: true
      t.string :name, null: true
    end
  end
end
